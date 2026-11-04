package health

import (
	"os"
	"testing"
)

func TestReadReportsFilesystemCapacityAndFreeSpace(t *testing.T) {
	root := t.TempDir()
	snapshot, err := Read(root)
	if err != nil {
		t.Fatal(err)
	}
	if snapshot.BytesAvailable == 0 {
		t.Fatal("filesystem capacity must be non-zero")
	}
	if snapshot.BytesFree == 0 || snapshot.BytesFree > snapshot.BytesAvailable {
		t.Fatalf("invalid filesystem free space: free=%d capacity=%d", snapshot.BytesFree, snapshot.BytesAvailable)
	}
}

func TestReadRejectsMissingRoot(t *testing.T) {
	_, err := Read(os.TempDir() + "/retr0-health-missing-root")
	if err == nil {
		t.Fatal("expected missing root error")
	}
}
