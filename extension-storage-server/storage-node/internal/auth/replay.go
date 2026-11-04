package auth

import (
	"bufio"
	"crypto/rand"
	"encoding/hex"
	"errors"
	"os"
	"strings"
	"sync"
	"time"
)

var ErrReplay = errors.New("replayed command")

type ReplayCache struct {
	mu      sync.Mutex
	entries map[string]time.Time
	limit   int
	path    string
}

func NewReplayCache(limit int) *ReplayCache { return NewPersistentReplayCache("", limit) }
func NewPersistentReplayCache(path string, limit int) *ReplayCache {
	if limit < 1 {
		limit = 1024
	}
	c := &ReplayCache{entries: make(map[string]time.Time), limit: limit, path: path}
	if path != "" {
		if f, err := os.Open(path); err == nil {
			s := bufio.NewScanner(f)
			for s.Scan() {
				p := strings.SplitN(s.Text(), " ", 2)
				if len(p) == 2 {
					if expiry, e := time.Parse(time.RFC3339Nano, p[1]); e == nil {
						c.entries[p[0]] = expiry
					}
				}
			}
			_ = f.Close()
		}
	}
	return c
}
func (c *ReplayCache) Consume(id string, expiresAt, now time.Time) bool {
	c.mu.Lock()
	defer c.mu.Unlock()
	for key, expiry := range c.entries {
		if !now.Before(expiry) {
			delete(c.entries, key)
		}
	}
	if id == "" || expiresAt.IsZero() || !expiresAt.After(now) {
		return false
	}
	if _, exists := c.entries[id]; exists || len(c.entries) >= c.limit {
		return false
	}
	if c.path != "" {
		if err := os.MkdirAll(filepathDir(c.path), 0o750); err != nil {
			return false
		}
		f, err := os.OpenFile(c.path, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0o600)
		if err != nil {
			return false
		}
		_, err = f.WriteString(id + " " + expiresAt.UTC().Format(time.RFC3339Nano) + "\n")
		_ = f.Close()
		if err != nil {
			return false
		}
	}
	c.entries[id] = expiresAt
	return true
}
func filepathDir(path string) string {
	if i := strings.LastIndexAny(path, `/\\`); i >= 0 {
		return path[:i]
	}
	return "."
}
func NewNodeID() (string, error) {
	b := make([]byte, 16)
	if _, e := rand.Read(b); e != nil {
		return "", e
	}
	return hex.EncodeToString(b), nil
}
