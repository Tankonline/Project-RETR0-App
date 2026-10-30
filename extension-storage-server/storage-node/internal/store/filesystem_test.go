package store

import (
	"bytes"
	"crypto/sha256"
	"encoding/hex"
	"os"
	"testing"
)

func TestPutAndOpen(t *testing.T) {
	root := t.TempDir()
	content := []byte("hello extension storage")
	digest := sha256.Sum256(content)
	hash := hex.EncodeToString(digest[:])
	storage := FileSystemStore{Root: root}
	if _, err := storage.Put(hash, int64(len(content)), bytes.NewReader(content)); err != nil {
		t.Fatal(err)
	}
	file, info, err := storage.Open(hash)
	if err != nil {
		t.Fatal(err)
	}
	defer file.Close()
	if info.Size() != int64(len(content)) {
		t.Fatalf("size = %d", info.Size())
	}
	got, err := os.ReadFile(file.Name())
	if err != nil {
		t.Fatal(err)
	}
	if !bytes.Equal(got, content) {
		t.Fatalf("content mismatch")
	}
}

func TestPutRejectsWrongHash(t *testing.T) {
	storage := FileSystemStore{Root: t.TempDir()}
	hash := "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
	if _, err := storage.Put(hash, 3, bytes.NewReader([]byte("bad"))); err == nil {
		t.Fatal("expected hash mismatch")
	}
}
