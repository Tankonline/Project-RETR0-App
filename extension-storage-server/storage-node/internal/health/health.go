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
	info, e := os.Stat(root)
	if e != nil {
		return Snapshot{}, e
	}
	available := uint64(info.Size())
	if runtime.GOOS == "windows" {
		available = 0
	}
	return Snapshot{Root: root, BytesAvailable: available, BytesFree: available}, nil
}
func EnsureDirectory(root string) error { return os.MkdirAll(root, 0750) }
