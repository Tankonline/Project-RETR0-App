package store

import (
	"bytes"
	"crypto/sha256"
	"encoding/hex"
	"io"
	"testing"
)

func testHash(content []byte) string {
	digest := sha256.Sum256(content)
	return hex.EncodeToString(digest[:])
}

func TestPutAndOpen(t *testing.T) {
	root := t.TempDir()
	content := []byte("hello extension storage")
	storage := FileSystemStore{Root: root}
	if _, err := storage.Put(testHash(content), int64(len(content)), bytes.NewReader(content)); err != nil {
		t.Fatal(err)
	}
	file, info, err := storage.Open(testHash(content))
	if err != nil {
		t.Fatal(err)
	}
	defer file.Close()
	if info.Size() != int64(len(content)) {
		t.Fatalf("size = %d", info.Size())
	}
	got, err := io.ReadAll(file)
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

func TestObjectPathRejectsTraversalAndMalformedHashes(t *testing.T) {
	storage := FileSystemStore{Root: t.TempDir()}
	for _, value := range []string{"", "../object", "zzzz", "abc"} {
		if _, err := storage.ObjectPath(value); err == nil {
			t.Fatalf("expected invalid hash for %q", value)
		}
	}
}
