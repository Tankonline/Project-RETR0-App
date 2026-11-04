//go:build windows

package health

import (
	"syscall"
	"unsafe"
)

var (
	kernel32                 = syscall.NewLazyDLL("kernel32.dll")
	getDiskFreeSpaceExProc   = kernel32.NewProc("GetDiskFreeSpaceExW")
)

func filesystemCapacity(root string) (uint64, uint64, error) {
	path, err := syscall.UTF16PtrFromString(root)
	if err != nil {
		return 0, 0, err
	}
	var freeBytes, totalBytes, totalFreeBytes uint64
	ret, _, callErr := getDiskFreeSpaceExProc.Call(
		uintptr(unsafe.Pointer(path)),
		uintptr(unsafe.Pointer(&freeBytes)),
		uintptr(unsafe.Pointer(&totalBytes)),
		uintptr(unsafe.Pointer(&totalFreeBytes)),
	)
	if ret == 0 {
		return 0, 0, callErr
	}
	return totalBytes, freeBytes, nil
}
