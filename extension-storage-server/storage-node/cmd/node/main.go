package main

import (
	"context"
	"errors"
	"flag"
	"log"
	"net/http"
	"os"
	"os/signal"
	"path/filepath"
	"syscall"
	"time"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/api"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/auth"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/controlplane"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/health"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
)

func main() {
	root := flag.String("storage-root", "./data", "directory used for objects")
	listen := flag.String("listen", "127.0.0.1:8788", "listen address")
	identityPath := flag.String("identity-file", "", "persistent node identity path")
	replayPath := flag.String("replay-file", "", "persistent replay journal path")
	quota := flag.Int64("quota-bytes", 0, "maximum stored bytes; zero means unlimited")
	environment := flag.String("control-plane-environment", "", "control-plane environment; only staging is supported")
	controlPlaneURL := flag.String("control-plane-url", "", "HTTPS staging control-plane URL")
	enrollmentCode := flag.String("enrollment-code", "", "one-time staging enrollment code")
	statePath := flag.String("control-plane-state-file", "", "persistent staging enrollment state path")
	transferHost := flag.String("staging-transfer-host", "save-files-staging.armaandeep08-ap.workers.dev", "fixed staging transfer hostname")
	heartbeatInterval := flag.Int("heartbeat-interval-seconds", 60, "staging heartbeat interval")
	flag.Parse()
	if e := health.EnsureDirectory(*root); e != nil {
		log.Fatal(e)
	}
	if *identityPath == "" {
		*identityPath = filepath.Join(*root, "node-identity.json")
	}
	id, e := auth.LoadOrCreateIdentity(*identityPath)
	if e != nil {
		log.Fatal(e)
	}
	pub, _ := id.Public()
	if *replayPath == "" {
		*replayPath = filepath.Join(*root, "replay.log")
	}
	if *statePath == "" {
		*statePath = filepath.Join(*root, "control-plane-state.json")
	}
	s := api.Server{Store: store.FileSystemStore{Root: *root, QuotaBytes: *quota}, NodeID: id.NodeID, ControlKey: pub, Replay: auth.NewPersistentReplayCache(*replayPath, 100000), AllowedTransferHost: *transferHost}
	server := &http.Server{Addr: *listen, Handler: s.Handler()}
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()
	if *controlPlaneURL != "" || *environment != "" || *enrollmentCode != "" {
		client := &controlplane.Client{
			Identity:  id,
			Config:    controlplane.Config{BaseURL: *controlPlaneURL, Environment: *environment, EnrollmentCode: *enrollmentCode, HeartbeatIntervalSeconds: *heartbeatInterval},
			StatePath: *statePath,
		}
		if state, err := controlplane.LoadState(*statePath); err == nil {
			client.State = state
		} else if !errors.Is(err, os.ErrNotExist) {
			log.Fatal(err)
		}
		if client.State.Status == "" {
			if err := client.Enroll(ctx); err != nil {
				log.Fatal(err)
			}
		}
		go func() {
			log.Printf("staging control-plane enabled node=%s state=%s interval_seconds=%d", id.NodeID, client.State.Status, client.Config.HeartbeatIntervalSeconds)
			_ = client.RunCommandLoop(ctx, func(commandCtx context.Context, command controlplane.CommandEnvelope) controlplane.CommandAck {
				return controlplane.ExecutePutObject(commandCtx, command, s.Store, http.DefaultClient, *transferHost)
			})
			err := client.RunHeartbeatLoop(ctx, func() (int64, int64, int64) {
				snap, e := health.Read(*root)
				if e != nil {
					log.Printf("staging heartbeat metrics_failed node=%s", id.NodeID)
					return 0, 0, 0
				}
				return int64(snap.BytesAvailable), int64(snap.BytesFree), int64(store.FileSystemStore{Root: *root}.UsedBytes())
			})
			if err != nil && !errors.Is(err, context.Canceled) {
				log.Printf("staging heartbeat stopped node=%s error=%v", id.NodeID, err)
			}
		}()
	}
	go func() {
		log.Printf("storage node %s listening on %s", id.NodeID, *listen)
		if err := server.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
			log.Fatal(err)
		}
	}()
	<-ctx.Done()
	shutdownCtx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	_ = server.Shutdown(shutdownCtx)
}
