# Storage Node

Local/staging-ready Go storage daemon. Existing local flags remain compatible:

```text
-storage-root -listen -identity-file -replay-file -quota-bytes
```

Optional staging enrollment flags are additive and never enable production:

```text
-control-plane-environment staging
-control-plane-url https://<staging-worker-host>/<staging-path>
-enrollment-code <one-time-code>
-control-plane-state-file <0600-state-file>
-heartbeat-interval-seconds 60
```

The control-plane URL must be HTTPS, explicit, non-private, and used only with `staging`. Enrollment state is persisted atomically with mode `0600`; the Ed25519 identity and replay journal remain under the storage root. Heartbeats use the canonical signed v1 protocol, with bounded retry/backoff, status/revocation handling, and graceful shutdown.

No production URL, admin token, production secret, live replication, or Android integration is included.
