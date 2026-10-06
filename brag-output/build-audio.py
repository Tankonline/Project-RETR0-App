"""Build the audio layer for the Project RETR0 brag video.

Outputs (into composition/assets/audio/):
  bed.mp3      - vol-11 music trimmed to 22s, fade-in/out, slight duck under the outro
  sfx-mix.wav  - all SFX pre-placed at their timeline timestamps (must match index.html)
  rms.js       - per-frame (30fps) bass-weighted energy of the bed for the audio-reactive halo
"""
import json, os, re, subprocess, sys, tempfile

HERE = os.path.dirname(os.path.abspath(__file__))
COMP = os.path.join(HERE, "composition")
OUT = os.path.join(COMP, "assets", "audio")
SFX_SRC = os.path.expanduser("~/.claude/plugins/cache/brag/brag/0.2.2/skills/brag/assets/sfx")
MUSIC = os.path.join(COMP, "assets", "music", "happy-beats-business-moves-vol-11-by-ende-dot-app.mp3")
DUR = 22.0
os.makedirs(OUT, exist_ok=True)

def run(cmd):
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(" ".join(cmd)); print(r.stderr[-2000:]); sys.exit(1)
    return r

# ---------------------------------------------------------------- music bed
bed = os.path.join(OUT, "bed.mp3")
run(["ffmpeg", "-y", "-i", MUSIC, "-t", str(DUR), "-af",
     "volume=0.44,"
     "volume='if(between(t,18.3,22),0.8,1)':eval=frame,"
     "afade=t=in:st=0:d=0.5,afade=t=out:st=20.8:d=1.2",
     "-ar", "44100", "-ac", "2", "-b:a", "192k", bed])

# ---------------------------------------------------------------- SFX cue list
# (time_s, file relative to SFX_SRC, gain). Times mirror the GSAP timeline in index.html.
cues = []
def add(t, f, g): cues.append((t, f, g))

# Scene 1 - hook typing (every 2nd char of 30, 0.10 + i*0.04)
for n, i in enumerate(range(0, 30, 2)):
    add(0.10 + i * 0.04, f"keyboard/keypress-{(n % 6) + 3:03d}.wav", 0.32)
add(1.50, "impact/impactSoft_medium_002.ogg", 0.55)      # status card slams in (red)
add(2.50, "interface/bong_001.ogg", 0.85)                # red -> green : Shizuku granted
add(3.70, "impact/impactSoft_heavy_003.ogg", 0.95)       # title slam        // beat-locked 3.70
# Scene 2 - skin swap
add(4.90, "casino/card-slide-3.ogg", 0.40)               # phone rises
add(6.40, "interface/click_003.ogg", 0.60)               # tap GET
add(6.45, "casino/card-slide-2.ogg", 0.32)               # sheet up
add(7.30, "interface/click_003.ogg", 0.60)               # tap APPLY
add(7.90, "casino/card-slide-4.ogg", 0.30)               # sheet down
add(8.15, "interface/bong_001.ogg", 0.85)                # Active
# Scene 3 - soundtrack swap
add(8.96, "casino/card-slide-1.ogg", 0.36)               # screen slide     // beat-locked 8.96
add(9.45, "interface/click_003.ogg", 0.55)               # tap field
for n, i in enumerate(range(0, 19, 2)):                  # URL typing 9.55 + i*0.04
    add(9.55 + i * 0.04, f"keyboard/keypress-{(n % 6) + 9:03d}.wav", 0.30)
add(10.50, "interface/click_003.ogg", 0.60)              # tap CONVERT
add(11.40, "interface/drop_001.ogg", 0.55)               # library row lands
add(11.85, "interface/click_003.ogg", 0.60)              # tap Apply
add(11.90, "casino/card-place-1.ogg", 0.35)              # dialog opens
add(12.40, "interface/click_003.ogg", 0.55)              # pick slot
add(12.65, "interface/bong_001.ogg", 0.85)               # replaced         // beat-locked 12.65
# Scene 4 - safety net
add(13.70, "casino/card-slide-2.ogg", 0.36)              # screen slide
add(14.25, "interface/click_003.ogg", 0.60)              # tap BACKUP
add(14.70, "interface/drop_001.ogg", 0.55)               # snapshot lands
add(15.45, "casino/card-slide-3.ogg", 0.36)              # screen slide
add(16.30, "interface/click_003.ogg", 0.60)              # tap Revert
add(16.75, "interface/bong_001.ogg", 0.85)               # restored
# Scene 5 - outro
add(17.91, "impact/impactSoft_heavy_000.ogg", 1.00)      # logo slam       // beat-locked 17.91
for k, t in enumerate((18.43, 18.96, 19.48)):            # tagline words on the beat grid
    add(t, "interface/click_005.ogg", 0.22)
add(20.00, "interface/bong_001.ogg", 0.55)               # "Race."

cues.sort()
inputs, filters, labels = [], [], []
for idx, (t, f, g) in enumerate(cues):
    path = os.path.join(SFX_SRC, f)
    if not os.path.exists(path):
        print("missing SFX:", path); sys.exit(1)
    inputs += ["-i", path]
    ms = int(round(t * 1000))
    filters.append(f"[{idx}:a]aformat=sample_rates=44100:channel_layouts=stereo,volume={g},adelay={ms}|{ms}[a{idx}]")
    labels.append(f"[a{idx}]")
graph = ";".join(filters) + ";" + "".join(labels) + f"amix=inputs={len(labels)}:normalize=0:duration=longest,atrim=0:{DUR},apad=whole_dur={DUR}[o]"
sfx = os.path.join(OUT, "sfx-mix.wav")
run(["ffmpeg", "-y", *inputs, "-filter_complex", graph, "-map", "[o]", "-ar", "44100", "-ac", "2", sfx])

# ---------------------------------------------------------------- bass envelope @30fps
tmp = tempfile.gettempdir()
txt = os.path.join(tmp, "retr0_rms.txt")
if os.path.exists(txt): os.remove(txt)
# bare filename + cwd=tmp: a Windows drive-letter colon would break the filter syntax
r = subprocess.run(["ffmpeg", "-y", "-i", bed, "-af",
     "aresample=8000,lowpass=f=220,asetnsamples=n=267:p=0,astats=metadata=1:reset=1,"
     "ametadata=print:key=lavfi.astats.Overall.RMS_level:file=retr0_rms.txt",
     "-f", "null", "-"], capture_output=True, text=True, cwd=tmp)
if r.returncode != 0:
    print(r.stderr[-1500:]); sys.exit(1)
vals = []
for line in open(txt):
    m = re.search(r"RMS_level=(-?[\d.]+|-inf)", line)
    if m:
        v = m.group(1)
        vals.append(-90.0 if v == "-inf" else float(v))
frames = int(DUR * 30)
vals = (vals + [vals[-1]] * frames)[:frames]
lo, hi = sorted(vals)[int(len(vals) * 0.05)], sorted(vals)[int(len(vals) * 0.95)]
env = [max(0.0, min(1.0, (v - lo) / (hi - lo))) for v in vals]
# light smoothing so the glow breathes instead of flickers
sm = []
for i in range(len(env)):
    w = env[max(0, i - 2): i + 3]
    sm.append(round(sum(w) / len(w), 3))
with open(os.path.join(OUT, "rms.js"), "w") as fh:
    fh.write("window.__RMS=" + json.dumps(sm) + ";\n")
print(f"ok: bed.mp3, sfx-mix.wav ({len(cues)} cues), rms.js ({len(sm)} frames)")
