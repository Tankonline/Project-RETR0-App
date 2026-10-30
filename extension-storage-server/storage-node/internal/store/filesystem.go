package store

import (
	"crypto/sha256"
	"encoding/hex"
	"errors"
	"fmt"
	"io"
	"os"
	"path/filepath"
)

var ErrInvalidHash = errors.New("invalid sha256")

// FileSystemStore stores immutable objects under a hash-derived path.
type FileSystemStore struct {
	Root string
}

func (s FileSystemStore) ObjectPath(hash string) (string, error) {
	if len(hash) != 64 {
		return "", ErrInvalidHash
	}
	if _, err := hex.DecodeString(hash); err != nil {
		return "", ErrInvalidHash
	}
	return filepath.Join(s.Root, "objects", "sha256", hash[:2], hash[2:4], hash), nil
}

func (s FileSystemStore) Put(hash string, expectedSize int64, content io.Reader) (int64, error) {
	path, err := s.ObjectPath(hash)
	if err != nil {
		return 0, err
	}
	if expectedSize < 0 {
		return 0, fmt.Errorf("negative object size")
	}
	if err := os.MkdirAll(filepath.Dir(path), 0o750); err != nil {
		return 0, err
	}
	tmp, err := os.CreateTemp(filepath.Dir(path), ".upload-*")
	if err != nil {
		return 0, err
	}
	tmpName := tmp.Name()
	defer os.Remove(tmpName)

	hasher := sha256.New()
	written, err := io.Copy(io.MultiWriter(tmp, hasher), content)
	if closeErr := tmp.Close(); err == nil {
		err = closeErr
	}
	if err != nil {
		return written, err
	}
	if written != expectedSize {
		return written, fmt.Errorf("size mismatch: got %d want %d", written, expectedSize)
	}
	if hex.EncodeToString(hasher.Sum(nil)) != hash {
		return written, fmt.Errorf("sha256 mismatch")
	}
	if err := os.Chmod(tmpName, 0o640); err != nil {
		return written, err
	}
	if err := os.Rename(tmpName, path); err != nil {
		if errors.Is(err, os.ErrExist) {
			return written, nil
		}
		return written, err
	}
	return written, nil
}

func (s FileSystemStore) Open(hash string) (*os.File, os.FileInfo, error) {
	path, err := s.ObjectPath(hash)
	if err != nil {
		return nil, nil, err
	}
	file, err := os.Open(path)
	if err != nil {
		return nil, nil, err
	}
	info, err := file.Stat()
	if err != nil {
		file.Close()
		return nil, nil, err
	}
	return file, info, nil
}

func (s FileSystemStore) Remove(hash string) error {
	path, err := s.ObjectPath(hash)
	if err != nil {
		return err
	}
	return os.Remove(path)
}
