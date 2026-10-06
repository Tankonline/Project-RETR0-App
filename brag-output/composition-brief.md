# Hyperframes Composition Brief: Project RETR0 installer

## Objective
Create a short launch-style brag video for Project RETR0 installer, an open-source Android app that swaps Real Racing 3 skins, soundtracks and saves (via Shizuku) with backup and one-tap revert.

## Output
- Composition directory: `brag-output/composition/`
- Rendered video: `brag-output/brag.mp4`
- Format: landscape — 1920x1080
- Duration: 22 seconds

## Source Material
- Project root: `C:\Users\armaa\AndroidStudioProjects\ProjectRETR0installer`
- Primary files read: `README.md`, `CHANGELOG.md`, `app/src/main/res/values/strings.xml`, `ui/theme/Color.kt`, `ui/theme/Type.kt`, `ui/components/RetroUi.kt`, `ui/skins/SkinsComponents.kt`, `ui/skins/SkinsModels.kt`, `MainActivity.kt` (composable outline), `docs/privacy-and-community-policy.md`
- Product name: Project RETR0 installer (screen title: `PROJECT RETR0`)
- Tagline / strongest claim: **Preserve. Modify. Restore. Race.** / Android locked the game files.
- Key UI or visual moment to recreate: dark card UI in a phone frame — SYSTEM STATUS row, Community Hub skin card with `GET`, `Download & apply` sheet, YouTube converter, library row + `SWAP`, `BACKUP CHARACTER DATA`, `Original backup available` + `Revert`.
- Copy that must appear verbatim:
  - `Android locked your game files.`
  - `Shizuku is not running.` → `Shizuku is running (permission granted).`
  - `PROJECT RETR0` · `for Real Racing 3`
  - `Swap the skin.` · `Swap the soundtrack.` · `Broke it? Revert it.`
  - `Aurora Drift` · `Porsche 911 RSR` · `NovaMods` · `Fits` · `GET` · `Download & apply` · `Active`
  - `YOUTUBE CONVERTER` · `Paste YouTube URL here…` · `CONVERT TO LIBRARY` · `SWAP`
  - `BACKUP CHARACTER DATA` · `Original backup available` · `Revert`
  - `Preserve. Modify. Restore. Race.`
  - `Free · Open source · GPL-3.0`
  - `Unofficial. Not affiliated with EA or Real Racing 3.`

## Creative Direction
- Tone preset: default
- Creative direction: "pit-stop swap" — fast, confident, a little cheeky, always readable.
- Interpretation: Warm and postable. One idea per scene, captions of three or four words, the real UI doing the talking. Cuts land on strong beats; nothing screams.
- Angle: Android locked the files; RETR0 is the pit crew that gets in, swaps the part, and gets out. Three swaps (skin, soundtrack, safety-net revert), each a real in-app flow, ending on the project's own tagline.
- Hook: Typed pixel line "Android locked your game files." then a real status row flips red → green (`Shizuku is not running.` → `Shizuku is running (permission granted).`).
- Outro / punchline: "Broke it? Revert it." then RETRO mark + `PROJECT RETR0` slam and the tagline builds one word per beat.
- Avoid:
  - Generic SaaS language
  - Abstract filler visuals
  - Unrelated visual redesign (stay faithful to the app's dark card UI)
  - **Any EA / Real Racing 3 logo or key art** (do not use `rr3_banner.jpg`); draw stylised abstract livery covers instead
  - Real song titles or real YouTube video IDs (use `youtube.com/watch?v=…` and `Night Drive.mp3`)

## Visual Identity
- Background: `#0B0D12` (surfaces `#161A22`, `#1E232E`, cover `#1A2030`; border `rgba(255,255,255,0.08)`)
- Text: `#F2F4F8` (soft `#C8CFDA`, dim `#9AA3B2`, muted `#6B7280`)
- Accent: `#3B82F6` (accent tint `rgba(59,130,246,.14)`); success `#34D399`; danger `#F87171`; gold `#F5C542` (sparingly)
- Display font: **PixelMood** — `app/src/main/res/font/hu_pixelmood_l.ttf` (copied to `composition/assets/fonts/`; load via `@font-face`)
- Body font: system sans / Roboto-like (the app uses Android default sans-serif)
- Visual references from the project: RetroCard (16px radius, 1px hairline border), blue full-width PrimaryButton (14px radius), pill BadgePill, uppercase letter-spaced section headers with hairline rule, black/white RETRO logo mark (`drawable-nodpi/project_retr0_game_art.jpg`, black bg — blend with `mix-blend-mode: screen`, copied to `composition/assets/img/retro-mark.jpg`).

## Storyboard
Use the storyboard in `brag-output/brag-plan.md` as the creative contract.

Scene summary:
1. Hook — 3.70s (0.00–3.70) — typed "Android locked your game files."; status row red → green
2. Reveal + skin swap — 5.26s (3.70–8.96) — `PROJECT RETR0` slam, then GET → Download & apply → Active; caption "Swap the skin."
3. Soundtrack swap — 4.74s (8.96–13.70) — paste URL → CONVERT TO LIBRARY → SWAP into slot; caption "Swap the soundtrack."
4. Safety net — 4.21s (13.70–17.91) — BACKUP → snapshot → Revert; caption "Broke it? Revert it."
5. Outro — 4.09s (17.91–22.00) — RETRO mark + name slam; tagline word per beat; footer + legal line

## Audio
- Audio role: warm bed with sparse professional accents
- Audio arc: music fades in under the typed hook; success chime on Shizuku unlock; motion-matched taps carry the three swaps; one weighted hit on the logo slam; music fades out over the last ~1.2s.
- Music: `happy-beats-business-moves-vol-11-by-ende-dot-app.mp3`
- Music treatment: fade in ~0.5s from 0:00, low-moderate bed under SFX, slight duck under outro tagline, ~1.2s fade-out ending at 22.0s.
- Music cue guidance: bundled preset `assets/music/cues/happy-beats-business-moves-vol-11-by-ende-dot-app.music-cues.{md,json}` (~114.84 BPM). Strong cues to lock: **3.70s** (scene 1→2 title slam), **8.96s** (skin→music cut), **17.91s** (outro logo slam). Use the beat grid (~0.52s spacing) for the cut near 13.70s and for outro tagline words from ~18.44s. Ignore any cue that hurts readability.
- Audio-reactive treatment: subtle — phone blue under-glow and logo halo breathe with RMS/bass. No waveform/equalizer visuals.
- Audio-coupled moments:
  - Scene 1 hook — typing (key ticks); red→green flip (single success chime)
  - Scene 2 — logo slam (weighted hit); cursor taps on GET / confirm; `Active` chip flip (chime)
  - Scene 3 — URL typing; CONVERT tap; progress fill; SWAP tap; lock-in (chime)
  - Scene 4 — backup tap + snapshot drop; Revert tap; restore (chime)
  - Scene 5 — final logo slam; tagline word ticks (very quiet, optional)
- SFX selection guidance: match sound to motion. Keys/clicks for typing and cursor taps, a short bright chime only for success states, soft whoosh for scene cuts, one heavier impact for the logo slam. Restraint: never stack SFX on every element.
- SFX analysis guidance: `~/.claude/plugins/cache/brag/brag/0.2.2/skills/brag/assets/sfx/sfx-analysis.md` (prefer low high-frequency-risk files for repeated sounds like taps and ticks).
- Exact SFX choice: Hyperframes should choose filenames, timestamps, density, and volume based on the implemented animation.
- Audio files: copy chosen music and SFX into `brag-output/composition/assets/`

## Hyperframes Instructions
Load the composition-building Hyperframes domain skills — `hyperframes-core`, `hyperframes-animation`, `hyperframes-creative`, `hyperframes-keyframes`, and `hyperframes-cli` — if available. /brag is its own workflow: do not enter the `hyperframes` entry-point intent interview and do not route into its generic promo / launch-video workflow. Prefer native Hyperframes conventions over anything in `/brag`.

Requirements:
- Show at least one real UI, copy, or visual element from the source project (this video shows many).
- Keep all text readable in the final render (settled holds per brag-plan: labels ≥0.8s, sentences ≥0.3s/word, hook ≥1.5s).
- Keep the video at 22 seconds (within 15-25s).
- Include the planned music/SFX layer.
- Treat `/brag` audio notes as guidance, not a fixed cue sheet. Choose SFX after the visual animation exists.
- Treat music cue metadata as optional timing hints; lock only 1-3 strong cues (3.70, 8.96, 17.91) within ±0.15s and mark them `// beat-locked`; snap outro tagline words to beat-grid within ±0.10s and mark `// beat-grid`. Flag: outro words are single-word reads, and the completed tagline holds ≥1.5s.
- Consider subtle audio-reactive glow/halo tied to music RMS/bass. If extraction is unavailable, document it and skip; do not block the render.
- Use local assets for audio, fonts, and images.
- Run `hyperframes check` before render — it is brag's single gate.
