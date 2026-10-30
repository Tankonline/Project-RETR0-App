# Storage Node

Planned Go daemon for computers and VPS servers that provide storage capacity.

The daemon will:

- receive only control-plane-authenticated commands;
- store opaque SHA-256-addressed blobs;
- support streaming reads and writes;
- report health and capacity;
- verify byte length and SHA-256 before acknowledging writes;
- reject direct user deletion;
- support node drain and revocation.

It will not contain Community Hub ownership, moderation, or publication logic.
