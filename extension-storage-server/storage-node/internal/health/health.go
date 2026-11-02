package health

import (
	"os"
)

type Snapshot struct {
	Root           string `json:"root"`
	BytesAvailable uint64 `json:"bytesAvailable"`
	BytesFree      uint64 `json:"bytesFree"`
}

func Read(root string) (Snapshot, error) {
	if _, err := os.Stat(root); err != nil {
		return Snapshot{}, err
	}
	available, free, err := filesystemCapacity(root)
	if err != nil {
		return Snapshot{}, err
	}
	return Snapshot{Root: root, BytesAvailable: available, BytesFree: free}, nil
}
func EnsureDirectory(root string) error { return os.MkdirAll(root, 0750) }
