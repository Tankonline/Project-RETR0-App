//go:build !windows

package health

import "syscall"

func filesystemCapacity(root string) (uint64, uint64, error) {
	var stat syscall.Statfs_t
	if err := syscall.Statfs(root, &stat); err != nil {
		return 0, 0, err
	}
	return uint64(stat.Blocks) * uint64(stat.Bsize), uint64(stat.Bavail) * uint64(stat.Bsize), nil
}
