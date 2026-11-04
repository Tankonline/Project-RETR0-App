import fs from "node:fs";
import vm from "node:vm";

const migrationFiles = [
  "migrations/0015_community_music_uploads.sql",
  "migrations-staging/0006_community_music_uploads.sql",
];
const required = [
  "community_music_uploads", "music_likes", "music_downloads", "file_key",
  "file_size", "file_sha256", "custom_name", "uploader_name", "target_track",
  "cover_key", "cover_sha256", "cover_content_type", "deleted_at", "created_at",
];
for (const file of migrationFiles) {
  const sql = fs.readFileSync(new URL(file, import.meta.url), "utf8").toLowerCase();
  for (const name of required) if (!sql.includes(name)) throw new Error(`${file}: missing ${name}`);
  for (const index of ["idx_community_music_uploads_created", "idx_community_music_uploads_owner", "idx_community_music_uploads_active", "idx_music_likes_music", "idx_music_downloads_music"])
    if (!sql.includes(index)) throw new Error(`${file}: missing ${index}`);
}
const worker = fs.readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
for (const name of ["community_music_uploads", "music_likes", "music_downloads", "file_key", "cover_key", "cover_content_type", "deleted_at"])
  if (!worker.includes(name)) throw new Error(`Worker reference missing ${name}`);
console.log("music schema/Worker reference guard passed");
