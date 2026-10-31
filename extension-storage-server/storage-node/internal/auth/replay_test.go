package auth

import (
	"testing"
	"time"
)

func TestReplayCacheConsumesOnce(t *testing.T) {
	now := time.Unix(100, 0)
	cache := NewReplayCache(2)
	if !cache.Consume("one", now.Add(time.Minute), now) {
		t.Fatal("first command should be accepted")
	}
	if cache.Consume("one", now.Add(time.Minute), now) {
		t.Fatal("replayed command should be rejected")
	}
	if !cache.Consume("two", now.Add(time.Minute), now) {
		t.Fatal("second command should be accepted")
	}
	if cache.Consume("three", now.Add(time.Minute), now) {
		t.Fatal("full cache should reject commands")
	}
}
