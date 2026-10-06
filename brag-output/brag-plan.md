# Brag Plan: Project RETR0 installer

## What is this app?
An open-source Android app that lets you swap the skins, soundtracks and saves in Real Racing 3 (through Shizuku, with backup and one-tap revert) after Android locked those game files away.

## The angle
**A pit stop, not a hack.** Android locked the files; RETR0 is the pit crew that gets in, swaps the part, and gets out. The video is built around the app's own verb: **SWAP** (the app literally ships `SWAP`, `GET`, `APPLY`, `REVERT` buttons). Three swaps, three real flows, then the safety net that makes it feel responsible: *Broke it? Revert it.*

What makes it specific to this project and not generic: real UI copy from the app (`Shizuku is not running.` → `Shizuku is running (permission granted).`, `Paste YouTube URL here…`, `CONVERT TO LIBRARY`, `Download & apply`, `Original backup available`), the PixelMood pixel font, and the project's own tagline: **Preserve. Modify. Restore. Race.**

## Hook (first 2-3 seconds)
Near-black screen. A line types out in pixel type: **"Android locked your game files."** Then a real status row from the app slams in red: `Shizuku is not running.` and flips green: `Shizuku is running (permission granted).` on a success sound. The problem, then the unlock, inside 3.7s.

## Key moments (the middle)
- **Skin swap** — Community Hub gallery card *Aurora Drift* (Porsche 911 RSR, by NovaMods, 1.2k likes, 4.8k downloads, green `Fits` badge). Cursor taps `GET` → `Download & apply` sheet → status chip flips to green `Active`.
- **Soundtrack swap** — Paste a YouTube link (typed live) → `CONVERT TO LIBRARY` with a progress bar → track lands in the library → `SWAP` onto a game track slot.
- **Safety net** — `BACKUP CHARACTER DATA` creates a snapshot; the `Original backup available` card is visible; cursor taps `Revert` and the active skin flips back.

## Outro / punchline
Right before the logo: **"Broke it? Revert it."** lands as the last highlight. Then on the strong beat the RETRO mark and `PROJECT RETR0` slam in, and the tagline builds one word per beat: **Preserve. Modify. Restore. Race.** Small footer: `Free · Open source · GPL-3.0` and `Unofficial. Not affiliated with EA or Real Racing 3.`

## User flow worth showing
1. **Skin:** open Community Hub → tap `GET` on a skin → `Download & apply` → skin shows `Active`.
2. **Music:** paste YouTube URL → `CONVERT TO LIBRARY` → library track → `SWAP` into a game slot.
3. **Backup/revert:** `BACKUP CHARACTER DATA` → snapshot saved → `Revert` restores the original.

(All three are real flows in `MainActivity.kt`, `ui/skins/*`, `strings.xml`.)

## Tone
- Preset: `default`
- Creative direction: "pit-stop swap": fast, confident, a little cheeky, always readable.
- Interpretation: Warm and postable. Each scene is one idea in three words or fewer of caption (*Swap the skin. Swap the soundtrack. Broke it? Revert it.*), with the real UI doing the talking. Cuts land on strong beats; nothing screams.

## Format: landscape — 1920x1080
Phone-shaped app UI sits right of centre in a device frame; big pixel-type captions on the left. (Vertical is an easy re-render if wanted.)
## Duration: 22 seconds

## Visual identity (from the project)
- Background: `#0B0D12` (RetroBg); surfaces `#161A22` / `#1E232E`; hairline border `rgba(255,255,255,.08)`
- Accent: `#3B82F6` (RetroAccent); success `#34D399`; danger `#F87171`; gold `#F5C542` (sparingly)
- Text: `#F2F4F8` primary, `#C8CFDA` soft, `#9AA3B2` dim, `#6B7280` muted
- Display font: **PixelMood** (`hu_pixelmood_l.ttf`, bundled at `app/src/main/res/font/`; copy into the composition assets)
- Body font: system sans (the app uses Android's default sans-serif, Roboto-like)
- Strongest visual element: the app's own dark card UI (RetroCard, blue PrimaryButton, pill badges, SYSTEM STATUS row) and the black/white **RETRO** logo mark (`app/src/main/res/drawable-nodpi/project_retr0_game_art.jpg`; black background, so blend with `screen` to sit on the navy).
- **Do NOT use** `rr3_banner.jpg` or any EA / Real Racing 3 logo or key art. It is EA's trademarked artwork and the project explicitly disclaims affiliation. Car skin covers are stylised abstract livery shapes drawn in HTML/SVG, not game screenshots. The name "Real Racing 3" appears as plain text only, as in the README.

## Share copy (draft)
Built a free, open-source Android app to swap Real Racing 3 skins, soundtracks and saves, with backup and one-tap revert. Preserve. Modify. Restore. Race. 🏁 github.com/Tankonline/Project-RETR0-App (unofficial, not affiliated with EA)

## Audio direction
- Role: warm, light rhythmic bed with sparse professional accents.
- Music: `happy-beats-business-moves-vol-11-by-ende-dot-app.mp3` (bundled, upbeat, clean, sits under UI SFX).
- Music treatment: start at 0:00 with a ~0.5s fade-in, bed at low-moderate volume (~-16 to -18 dB posture), duck slightly under the outro tagline, ~1.2s fade-out ending at 22.0s.
- Music cue guidance: preset read (`vol-11`, ~114.84 BPM, beat every ~0.52s). Strong cues in window: **3.70s** (scene 1→2 title slam), **8.96s** (skin→music cut), **17.91s** (outro logo slam). Additional strong beats at 5.80s, 12.65s, 22.65s (past end; unused). Beat grid near 13.18 / 13.70 used for the music→revert cut, and outro words snap to the ~0.52s grid from 18.44s. Major reveals may nudge ≤0.15s toward a strong cue; small entrances ≤0.10s; ignore cues when they hurt readability.
- Audio-reactive treatment: subtle; use music RMS/bass to make the phone's blue under-glow and the logo halo breathe slightly. No waveform or equalizer visuals.
- SFX posture: moderate, motion-matched, professional restraint: key ticks on typing, soft taps on cursor clicks, a success chime on each "granted / Active / restored" flip, a light whoosh on scene cuts, one weighted hit on the logo slam.
- Audio-coupled moments: hook typing, Shizuku red→green flip, cursor taps on GET / CONVERT / SWAP / Revert, YouTube URL typing, progress fill, tagline word-per-beat.
- Restraint rule: audio must never mask or rush a readable line, no SFX stacking on every element, no chime on non-success events.

## Storyboard

### Scene 1 — Hook: "Android locked your game files." — 3.70s (0.00–3.70)
Near-black `#0B0D12`. Pixel-type line **"Android locked your game files."** types in (0.10–1.30s) and stays fully settled to the cut (≈5 words, well past the 1.5s floor). At ~1.50s a real app status card (SYSTEM STATUS style) slams in reading `Shizuku: Shizuku is not running.` with a red dot (holds 1.0s). At **2.50s** it flips to green `Shizuku is running (permission granted).` (holds 1.2s into the cut).
Sequential/interaction: yes — typed hook text; status row state flips red → green.
Audio intent: tension, then release.
Audio-coupled idea: subtle key ticks under the typing; a single soft success chime on the flip.
Music: bed fades in from silence; first strong beat sets up the 3.70s cut.
Transition mood: hard, beat-locked cut on the **3.70s** strong cue → Scene 2.

### Scene 2 — Reveal + skin swap: "Swap the skin." — 5.26s (3.70–8.96)
3.70–5.10: **`PROJECT RETR0`** slams in huge in pixel type with the RETRO mark and small sub-line `for Real Racing 3`; weighted hit on the beat, then it slides left and shrinks into a caption column. From ~4.9s the phone frame rises in at right showing the Community Hub gallery. Featured card **Aurora Drift**: `Porsche 911 RSR`, by `NovaMods`, ♥ 1.2k, ↓ 4.8k, `Fits` badge, abstract livery cover. Caption **"Swap the skin."** settles by ~5.4s (3 words, holds ≥0.8s).
Interaction: cursor moves in, taps `GET` (~6.3s) → `Download & apply` sheet slides up (`Porsche 911 RSR · Exterior · Slot 1`) → taps confirm (~7.4s) → sheet closes and the status chip flips to green **Active** (~8.0s) with a success chime; holds to the cut.
Sequential/interaction: yes — simulated cursor tap on GET, sheet, confirm, chip flip.
Audio intent: confident, satisfying swap.
Audio-coupled idea: soft tap on each click; chime on `Active`.
Music: bed continues; the 3.70 slam lands on the strong beat, `Active` chime near the 8.96 cut.
Transition mood: clean slide → Scene 3, on the **8.96s** strong cue.

### Scene 3 — Soundtrack swap: "Swap the soundtrack." — 4.74s (8.96–13.70)
Phone shows the YouTube converter (`YOUTUBE CONVERTER` header, hint `Convert YouTube links to MP3s in your Local Library.`). Caption **"Swap the soundtrack."** on the left (3 words). Simulated typing fills `Paste YouTube URL here…` with a generic `youtube.com/watch?v=…` URL (no real video or song title) → cursor taps `CONVERT TO LIBRARY` → progress bar fills → a library row appears (`Night Drive.mp3`) → cursor taps `SWAP` → row flies onto a game track slot (`Replace Soundtrack` dialog style) and locks in with a green check.
Sequential/interaction: yes — typed URL, click, progress fill, click, slot lock-in. Steps are 3 distinct beats at ~0.9–1.2s spacing; the caption is static so nothing readable is rushed.
Audio intent: playful process satisfaction.
Audio-coupled idea: key ticks for the URL; progress uses a rising subtle tone-free tick; soft tap on SWAP; light chime on lock-in.
Music: bed continues; SWAP lock-in near the 12.65 strong beat.
Transition mood: clean wipe → Scene 4 at ~**13.70s** (beat grid).

### Scene 4 — Safety net: "Broke it? Revert it." — 4.21s (13.70–17.91)
Phone shows the save hub, then the skin library. `BACKUP CHARACTER DATA` is tapped and a snapshot row drops in. Then the `Original backup available` card (green title) is shown next to the active skin card with `Revert` button. Caption **"Broke it? Revert it."** (4 words, settled by ~14.9s, holds ≥1.3s). Cursor taps `Revert`; the `Active` chip clears and reads original again with a success chime.
Sequential/interaction: yes — tap backup, snapshot appears, tap Revert, state restores.
Audio intent: reassurance; the "responsible" beat.
Audio-coupled idea: tap + drop; a single chime on restore.
Music: bed slightly building toward the 17.91 strong beat.
Transition mood: dramatic-but-clean cut with a brief brightness build → Scene 5 on **17.91s** strong cue.

### Scene 5 — Outro: "Preserve. Modify. Restore. Race." — 4.09s (17.91–22.00)
On the **17.91s** strong cue the RETRO mark and `PROJECT RETR0` slam in on the navy background with a halo that breathes with the bass. Tagline builds **one word per beat** from ~18.44s on the grid — **Preserve. Modify. Restore. Race.** — each word stays on screen and the completed line **holds ≥1.5s** before the end. Small footer fades in beneath: `Free · Open source · GPL-3.0`, and a tiny legal line `Unofficial. Not affiliated with EA or Real Racing 3.` (readable but low-emphasis; holds to the end). Music fades out over the last ~1.2s.
Sequential/interaction: yes — tagline words snap in one per beat (flagged: each word is a single-word read, cumulative line holds ≥1.5s).
Audio intent: payoff and a clean landing.
Audio-coupled idea: one weighted hit on the slam; soft tick per tagline word (optional, very quiet).
Music: strong beat at 17.91; fade-out to silence at 22.00.
Transition mood: end card, fade to black.

**Total: 3.70 + 5.26 + 4.74 + 4.21 + 4.09 = 22.00s** (within 15–25s).

**Music mood for this video:** upbeat, warm, clean
**Audio summary:** A light beat fades in under a typed hook, a success chime marks the Shizuku unlock, motion-matched taps carry the three swaps, and the bed swells into a single weighted logo hit before fading out.
