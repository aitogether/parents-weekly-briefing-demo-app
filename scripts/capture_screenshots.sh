#!/usr/bin/env bash
set -e

mkdir -p screenshots

# Wait for device fully booted
adb wait-for-device
until [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do sleep 2; done
sleep 5

# Install APK
echo "Installing APK..."
adb install -g app/build/outputs/apk/debug/app-debug.apk

# Dismiss system dialogs
adb shell input keyevent 82
sleep 1

# Launch app
adb shell monkey -p com.aitogether.parentswb -c android.intent.category.LAUNCHER 1
sleep 10

# --- Helper: capture screenshot ---
capture() {
  adb exec-out screencap -p > "screenshots/${1}.png"
  local sz
  sz=$(wc -c < "screenshots/${1}.png")
  echo "Captured: ${1}.png (${sz} bytes)"
}

# --- Helper: dump UI and list all clickable nodes ---
dump_and_list() {
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
  adb pull /sdcard/ui.xml screenshots/ui.xml >/dev/null 2>&1
  python3 scripts/list_buttons.py
}

# --- Helper: tap button by keyword match ---
tap_button() {
  local keyword="$1"
  local coords
  coords=$(KEYWORD="$keyword" python3 -c "
import os, xml.etree.ElementTree as ET, re, sys
kw = os.environ.get('KEYWORD','')
tree = ET.parse('screenshots/ui.xml')
for n in tree.getroot().iter('node'):
    t = n.get('text','')
    d = n.get('content-desc','')
    combined = t + ' ' + d
    if kw in combined and n.get('clickable','false') == 'true':
        m = re.match(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', n.get('bounds',''))
        if m:
            x1,y1,x2,y2 = map(int, m.groups())
            print(f'{(x1+x2)//2} {(y1+y2)//2}')
            sys.exit(0)
print('NOT_FOUND')
")
  if [ "$coords" = "NOT_FOUND" ] || [ -z "$coords" ]; then
    echo "Button '${keyword}' not found"
    return 1
  fi
  echo "Tapping '${keyword}' at ${coords}"
  adb shell input tap ${coords}
  sleep 4
  return 0
}

go_back() {
  adb shell input keyevent KEYCODE_BACK
  sleep 3
}

# === Step 1: Try to dismiss privacy dialog ===
echo "=== Dismiss privacy dialog ==="
adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
adb pull /sdcard/ui.xml screenshots/ui.xml >/dev/null 2>&1
# Dump all clickable elements for debugging
echo "--- All clickable elements on initial screen ---"
dump_and_list
echo "---"

for kw in "我知道了" "同意" "允许" "确认"; do
  tap_button "$kw" && break
done || true
sleep 3

# === Step 2: Dump UI again after dialog dismiss ===
adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
adb pull /sdcard/ui.xml screenshots/ui.xml >/dev/null 2>&1
echo "--- All clickable elements after dialog ---"
dump_and_list
echo "---"

# === Step 3: Home ===
echo "=== 01: Home ==="
capture "01-home"

# === Step 4: Navigate to each page ===
# Keywords must match substrings in the button text
PAGES=(
  "黄灯周报|02-report"
  "步数趋势|03-step-chart"
  "多周趋势|04-multi-week-trend"
  "焦虑自查|05-anxiety-survey"
  "妈妈|06-medication-mom"
  "爸爸|07-medication-dad"
  "用药计划|08-medication-plan"
)

for entry in "${PAGES[@]}"; do
  keyword="${entry%%|*}"
  filename="${entry##*|}"
  echo "=== ${filename} ==="
  if tap_button "$keyword"; then
    capture "$filename"
    go_back
  fi
done

echo ""
echo "=== All screenshots captured ==="
ls -lh screenshots/*.png
