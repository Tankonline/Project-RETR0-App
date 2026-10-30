# Control Plane

Planned Cloudflare Worker and D1 coordination layer.

Responsibilities:

- enroll and revoke extension nodes;
- issue signed, short-lived storage commands;
- track objects and replicas;
- queue replication and repair;
- verify node responses;
- route reads to healthy replicas;
- fall back to canonical R2;
- expose admin-only deletion and audit operations.

The Android application should continue using the existing Community Hub API.
This service will be integrated behind that API rather than exposed directly to
clients.
