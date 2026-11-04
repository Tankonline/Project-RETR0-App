# Project RETR0 Storage Provider GUI

Planned Tauri 2 + React desktop application for Windows and Linux.

The provider GUI will manage only the local storage node:

- choose storage directory and quota;
- start, stop, restart, and drain the node;
- complete enrollment and show approval state;
- view capacity, heartbeat, replication, and diagnostics;
- export redacted logs.

It will not contain Cloudflare API tokens, Android installation keys, ownership
controls, moderation controls, or arbitrary object deletion commands.

The GUI is intentionally scaffolded after the protocol and node behavior are
stable. Its first integration target is the loopback health/capacity API.
