package main

import (
	"context"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"os"
	"path/filepath"
	"runtime"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/auth"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/controlplane"
)

func main() {
	root := flag.String("storage-root", "./data", "node storage directory")
	identityPath := flag.String("identity-file", "", "node identity JSON path")
	statePath := flag.String("control-plane-state-file", "", "optional local state JSON path")
	controlPlaneURL := flag.String("control-plane-url", "", "HTTPS staging Worker URL")
	enrollmentCode := flag.String("enrollment-code", "", "pending enrollment code")
	flag.Parse()
	if *identityPath == "" {
		*identityPath = filepath.Join(*root, "node-identity.json")
	}
	if *statePath == "" {
		*statePath = filepath.Join(*root, "control-plane-state.json")
	}
	id, err := loadIdentity(*identityPath)
	if err != nil {
		fmt.Fprintln(os.Stderr, "error_code=identity_unavailable")
		os.Exit(2)
	}
	if *controlPlaneURL == "" {
		fmt.Fprintln(os.Stderr, "error_code=staging_url_required")
		os.Exit(2)
	}
	fmt.Fprint(os.Stderr, "Staging admin token: ")
	tokenBytes, err := readPassword()
	fmt.Fprintln(os.Stderr)
	if err != nil || len(tokenBytes) == 0 {
		fmt.Fprintln(os.Stderr, "error_code=token_unavailable")
		os.Exit(2)
	}
	client := &controlplane.Client{Identity: id, Config: controlplane.Config{Environment: "staging", BaseURL: *controlPlaneURL}, StatePath: *statePath}
	result, err := client.Approve(context.Background(), string(tokenBytes), *enrollmentCode)
	for i := range tokenBytes {
		tokenBytes[i] = 0
	}
	if err != nil {
		fmt.Printf("status=error request_id=%s error_code=%s\n", result.RequestID, safeCode(result.ErrorCode))
		os.Exit(1)
	}
	fmt.Printf("status=%s request_id=%s\n", result.Status, result.RequestID)
}

func loadIdentity(path string) (auth.Identity, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return auth.Identity{}, err
	}
	var id auth.Identity
	if err := json.Unmarshal(data, &id); err != nil || id.NodeID == "" {
		return auth.Identity{}, errors.New("invalid identity")
	}
	if _, err := id.Public(); err != nil {
		return auth.Identity{}, err
	}
	return id, nil
}
func safeCode(code string) string {
	if code == "" {
		return "admin_approval_failed"
	}
	return code
}

func readPassword() ([]byte, error) {
	if runtime.GOOS != "windows" {
		return readLine()
	}
	return readLine()
}
func readLine() ([]byte, error) {
	var value []byte
	buf := make([]byte, 1)
	for {
		n, err := os.Stdin.Read(buf)
		if err != nil {
			return nil, err
		}
		if n == 1 && (buf[0] == '\n' || buf[0] == '\r') {
			return value, nil
		}
		if n == 1 {
			value = append(value, buf[0])
		}
	}
}
