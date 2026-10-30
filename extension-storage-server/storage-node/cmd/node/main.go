package main

import (
	"flag"
	"log"
	"net/http"
	"os"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/api"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/health"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
)

func main() {
	root := flag.String("storage-root", "./data", "directory used for content-addressed objects")
	listen := flag.String("listen", "127.0.0.1:8788", "local management listen address")
	flag.Parse()
	if err := health.EnsureDirectory(*root); err != nil {
		log.Fatalf("create storage root: %v", err)
	}
	server := api.Server{Store: store.FileSystemStore{Root: *root}}
	log.Printf("storage node listening on %s", *listen)
	if err := http.ListenAndServe(*listen, server.Handler()); err != nil {
		log.Fatal(err)
	}
	_ = os.Stdout
}
