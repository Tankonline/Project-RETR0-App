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
var ErrQuotaExceeded = errors.New("quota exceeded")

type FileSystemStore struct {
	Root                string
	QuotaBytes          int64
	AllowedContentTypes map[string]bool
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
func (s FileSystemStore) UsedBytes() int64 {
	var total int64
	_ = filepath.Walk(s.Root, func(_ string, info os.FileInfo, err error) error {
		if err == nil && info.Mode().IsRegular() {
			total += info.Size()
		}
		return nil
	})
	return total
}
func (s FileSystemStore) Put(hash string, expectedSize int64, content io.Reader, contentType ...string) (int64, error) {
	path, err := s.ObjectPath(hash)
	if err != nil {
		return 0, err
	}
	if expectedSize < 0 {
		return 0, fmt.Errorf("negative object size")
	}
	if s.QuotaBytes > 0 && s.UsedBytes()+expectedSize > s.QuotaBytes {
		return 0, ErrQuotaExceeded
	}
	if len(contentType) > 0 && len(s.AllowedContentTypes) > 0 && !s.AllowedContentTypes[contentType[0]] {
		return 0, fmt.Errorf("content type not allowed")
	}
	if err = os.MkdirAll(filepath.Dir(path), 0750); err != nil {
		return 0, err
	}
	tmp, err := os.CreateTemp(filepath.Dir(path), ".upload-*")
	if err != nil {
		return 0, err
	}
	name := tmp.Name()
	defer os.Remove(name)
	h := sha256.New()
	written, err := io.Copy(io.MultiWriter(tmp, h), content)
	if closeErr := tmp.Close(); err == nil {
		err = closeErr
	}
	if err != nil {
		return written, err
	}
	if written != expectedSize {
		return written, fmt.Errorf("size mismatch: got %d want %d", written, expectedSize)
	}
	if hex.EncodeToString(h.Sum(nil)) != hash {
		return written, fmt.Errorf("sha256 mismatch")
	}
	if err = os.Chmod(name, 0640); err != nil {
		return written, err
	}
	if err = os.Rename(name, path); err != nil {
		if errors.Is(err, os.ErrExist) {
			return written, nil
		}
		return written, err
	}
	return written, nil
}
func (s FileSystemStore) Open(hash string) (*os.File, os.FileInfo, error) {
	p, e := s.ObjectPath(hash)
	if e != nil {
		return nil, nil, e
	}
	f, e := os.Open(p)
	if e != nil {
		return nil, nil, e
	}
	i, e := f.Stat()
	if e != nil {
		f.Close()
		return nil, nil, e
	}
	return f, i, nil
}
func (s FileSystemStore) Remove(hash string) error {
	p, e := s.ObjectPath(hash)
	if e != nil {
		return e
	}
	return os.Remove(p)
}
