package health

import (
	"os"
	"runtime"
)

type Snapshot struct {
	Root           string `json:"root"`
	BytesAvailable uint64 `json:"bytesAvailable"`
	BytesFree      uint64 `json:"bytesFree"`
}

func Read(root string) (Snapshot, error) {
	info, err := os.Stat(root)
	if err != nil {
		return Snapshot{}, err
	}
	// Portable baseline for the first milestone. Platform-specific disk quota
	// providers can replace this without changing the HTTP contract.
	available := uint64(info.Size())
	if runtime.GOOS == "windows" {
		available = 0
	}
	return Snapshot{Root: root, BytesAvailable: available, BytesFree: available}, nil
}

func EnsureDirectory(root string) error {
	return os.MkdirAll(root, 0o750)
}
