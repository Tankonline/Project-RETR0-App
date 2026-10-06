# Manual Worker avatar verification checklist

The exported Worker package was syntax-checked by extracting its JavaScript part to a temporary `.mjs` file. The multipart package itself is not raw JavaScript and must be imported through Cloudflare's corresponding package/import flow.

## Test cases after migration and manual import

- [ ] Missing signature on `PUT /v1/installations/avatar` returns an existing authentication error and creates no R2 object.
- [ ] Body larger than 200 KiB returns `413 body_too_large`.
- [ ] Empty body returns `400 empty_image`.
- [ ] MIME/magic mismatch returns `415 image_type_mismatch`.
- [ ] Valid signed WebP upload returns `success`, SHA-256, size, MIME, and versioned `avatarUrl`.
- [ ] `GET` returns the image stream with content type, length, ETag, no-sniff, and immutable cache headers.
- [ ] `HEAD` returns matching headers and no body.
- [ ] A stale `v` query value returns 404 after replacement.
- [ ] A replacement returns a new versioned URL and preserves the previous avatar until D1 update succeeds.
- [ ] `DELETE /v1/installations/avatar` is successful when an avatar exists and when it does not.
- [ ] `/v1/saves`, `/v1/music`, `/v1/skins`, and `/v1/skins/{id}` return nullable avatar URL fields without removing existing fields.
- [ ] Existing save, music, skin, legacy, and scheduled paths still work.
- [ ] Deleting an installation clears avatar metadata and asynchronously removes its `avatars/{installationId}/` prefix.
