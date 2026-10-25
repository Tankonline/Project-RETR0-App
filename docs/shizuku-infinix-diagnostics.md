# Shizuku / XOS reliability diagnostics

## Evidence collected by the app

The main activity logs the following checkpoints with tag `RETR0_INSTALLER`:

- `SHIZUKU_PERMISSION_REQUEST`: SDK binder availability before requesting permission.
- `SHIZUKU_USVC_CONNECTED`: UserService callback and binder validity.
- `SHIZUKU_USVC_STALE_CALLBACK`: callback ignored after the activity stopped.
- `SHIZUKU_USVC_TIMEOUT`: bind callback timeout, including current binder and permission state.
- `RETR0 user service binder died`: binder death after a successful connection.

`Retr0UserService` logs native library loading and AIDL operation stages with tag
`RETR0_INSTALLER_SVC`. The service is a Shizuku UserService; it is intentionally
not declared as an Android manifest service. The only manifest provider is the
SDK-owned `rikka.shizuku.ShizukuProvider`.

## Collection procedure

1. Confirm Shizuku is running and RETR0 permission is granted.
2. Reproduce the failure once, then use the app's log export action.
3. Capture `RETR0_INSTALLER`, `RETR0_INSTALLER_SVC`, and Shizuku manager logs around the failure.
4. Record whether the failure is before `SHIZUKU_USVC_CONNECTED`, after it, or during an AIDL call.
5. Record APK version, Android/XOS version, Shizuku version, and whether the app was backgrounded during the attempt.

## Current implementation boundaries

- No OEM-specific branches or hidden/unsafe APIs are used.
- UserService binding is attempted only when the Shizuku SDK reports a live binder and permission.
- A bind is not considered successful until a live binder arrives in `onServiceConnected`.
- Timeout and binder-death paths clear stale state and retry only while the activity is started.
- Release R8 rules keep the UserService, AIDL interface/stubs, activity, and SDK classes.
- Native packaging currently declares `armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`; physical-device ABI verification remains required.

## Verification gate

A physical Infinix Hot 60i/XOS run is still required. Until that run is available,
this document does not claim the OEM failure is resolved. The gate is to reproduce,
connect, exercise one harmless read-only operation, background/foreground the app,
and confirm the exported logs contain the expected lifecycle checkpoints.
