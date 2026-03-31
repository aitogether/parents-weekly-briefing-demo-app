#!/usr/bin/env bash
set -e

mkdir -p screenshots

# Wait for device fully booted
adb wait-for-device
until [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do sleep 2; done
sleep 5

# Set high-res display
adb shell wm size 1080x2400
adb shell wm density 420

# Install APK
echo "Installing APK..."
adb install -g app/build/outputs/apk/debug/app-debug.apk

# Dismiss system dialogs
adb shell input keyevent 82
sleep 1

# Launch app
adb shell monkey -p com.aitogether.parentswb -c android.intent.category.LAUNCHER 1
sleep 8

# --- Helper: capture screenshot ---
capture() {
  local name="$1"
  adb exec-out screencap -p > "screenshots/${name}.png"
  echo "✓ ${name}.png ($(wc -c < "screenshots/${name}.png") bytes)"
}

# --- Helper: dump UI and find button center by keyword ---
find_button() {
  local keyword="$1"
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
  adb pull /sdcard/ui.xml screenshots/ui.xml >/dev/null 2>&1
  python3 -c "
import xml.etree.ElementTree as ET, re, sys
kw = '${keyword}'
tree = ET.parse('screenshots/ui.xml')
for n in tree.getroot().iter('node'):
    t = n.get('text','') + ' ' + n.get('content-desc','')
    if kw in t and n.get('clickable','false') == 'true':
        m = re.match(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', n.get('bounds',''))
        if m:
            x1,y1,x2,y2 = map(int, m.groups())
            print(f'{(x1+x2)//2} {(y1+y2)//2}')
            sys.exit(0)
sys.exit(1)
" 2>/dev/null
}

# --- Helper: click button by keyword ---
click() {
  local coords
  coords=$(find_button "$1") || true
  if [ -n "$coords" ]; then
    echo "→ Clicking '$1' at $coords"
    adb shell input tap $coords
    sleep 4
    return 0
  else
    echo "✗ Button '$1' not found"
    return 1
  fi
}

go_back() {
  adb shell input keyevent KEYCODE_BACK
  sleep 3
}

# === Step 1: Dismiss privacy dialog ===
echo "=== Dismiss privacy dialog ==="
adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
adb pull /sdcard/ui.xml screenshots/ui.xml >/dev/null 2>&1
for kw in "我知道了" "同意" "允许" "确认"; do
  click "$kw" && break
done || true
sleep 3

# === Step 2: Home ===
echo "=== 01: Home ==="
capture "01-home"

# === Step 3-8: Navigate to each page ===
PAGES=(
  "周报|02-report"
  "步数|03-step-chart"
  "多周|04-multi-week-trend"
  "焦虑|05-anxiety-survey"
  "妈妈|06-medication-mom"
  "爸爸|07-medication-dad"
  "用药计划|08-medication-plan"
)

for entry in "${PAGES[@]}"; do
  keyword="${entry%%|*}"
  filename="${entry##*|}"
  echo "=== ${filename} ==="
  if click "$keyword"; then
    capture "$filename"
    go_back
  fi
done

echo ""
echo "=== All screenshots captured ==="
ls -lh screenshots/*.png
