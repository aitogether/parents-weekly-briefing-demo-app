#!/usr/bin/env bash
set -e

mkdir -p screenshots

# Wait for device fully booted
adb wait-for-device
until [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do sleep 2; done
sleep 5

# Get screen size
SCREEN_SIZE=$(adb shell wm size | grep -oP '\d+x\d+$')
WIDTH=$(echo $SCREEN_SIZE | cut -dx -f1)
HEIGHT=$(echo $SCREEN_SIZE | cut -dx -f2)
echo "Screen size: ${WIDTH}x${HEIGHT}"

# Install APK
echo "Installing APK..."
adb install -g app/build/outputs/apk/debug/app-debug.apk

# Dismiss system dialogs
adb shell input keyevent 82
sleep 1

# --- Helper: force-stop app, relaunch, wait for home ---
relaunch() {
  adb shell am force-stop com.aitogether.parentswb
  sleep 1
  adb shell monkey -p com.aitogether.parentswb -c android.intent.category.LAUNCHER 1
  sleep 8
}

# --- Helper: capture screenshot ---
capture() {
  adb exec-out screencap -p > "screenshots/${1}.png"
  echo "Captured: ${1}.png ($(wc -c < "screenshots/${1}.png") bytes)"
}

# --- Helper: tap at center X, given Y ---
tap_center() {
  local y_pos=$1
  local cx=$((WIDTH / 2))
  adb shell input tap $cx $y_pos
  sleep 4
}

# For 1080x2280 (pixel_4 emulator):
# Button start y≈245, each button ≈155px + 24px gap
# Button centers: 322, 482, 642, 802, 962, 1122, 1282

CONTENT_TOP=245
BUTTON_H=155
BUTTON_GAP=24
CENTER_X=$((WIDTH / 2))

button_y() {
  local index=$1  # 0-based
  echo $(( CONTENT_TOP + BUTTON_H / 2 + index * (BUTTON_H + BUTTON_GAP) ))
}

# === Step 1: Home ===
echo "=== 01: Home ==="
relaunch
capture "01-home"

# === Step 2: 黄灯周报 (button 0) ===
echo "=== 02: Report ==="
relaunch
tap_center $(button_y 0)
capture "02-report"

# === Step 3: 步数趋势 (button 1) ===
echo "=== 03: StepChart ==="
relaunch
tap_center $(button_y 1)
capture "03-step-chart"

# === Step 4: 多周趋势 (button 2) ===
echo "=== 04: MultiWeekTrend ==="
relaunch
tap_center $(button_y 2)
capture "04-multi-week-trend"

# === Step 5: 焦虑自查 (button 3) ===
echo "=== 05: AnxietySurvey ==="
relaunch
tap_center $(button_y 3)
capture "05-anxiety-survey"

# === Step 6: 妈妈用药 (button 4) ===
echo "=== 06: MedicationMom ==="
relaunch
tap_center $(button_y 4)
capture "06-medication-mom"

# === Step 7: 爸爸用药 (button 5) ===
echo "=== 07: MedicationDad ==="
relaunch
tap_center $(button_y 5)
capture "07-medication-dad"

# === Step 8: 用药计划 (button 6) ===
echo "=== 08: MedicationPlan ==="
relaunch
tap_center $(button_y 6)
capture "08-medication-plan"

echo ""
echo "=== All screenshots captured ==="
ls -lh screenshots/*.png
