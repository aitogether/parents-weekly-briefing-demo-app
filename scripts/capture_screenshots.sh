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

# Launch app
adb shell monkey -p com.aitogether.parentswb -c android.intent.category.LAUNCHER 1
sleep 10

# --- Helper: capture screenshot ---
capture() {
  adb exec-out screencap -p > "screenshots/${1}.png"
  echo "Captured: ${1}.png ($(wc -c < "screenshots/${1}.png") bytes)"
}

# --- Helper: tap at center of screen, specific Y offset ---
tap_center() {
  local y_offset=$1
  local cx=$((WIDTH / 2))
  adb shell input tap $cx $y_offset
  sleep 3
}

go_back() {
  adb shell input keyevent KEYCODE_BACK
  sleep 3
}

# For 1080x2280 (pixel_4 emulator):
# Measured from screenshot:
#   Status bar ~44px, App bar ~80px, Echo card ~75px, "请选择演示场景" ~35px
#   Button start y≈245, each button ≈155px + 24px gap
#   Button centers: 322, 482, 642, 802, 962, 1122, 1282

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
capture "01-home"

# === Step 2: 黄灯周报 ===
echo "=== 02: Report ==="
tap_center $(button_y 0)
capture "02-report"
go_back

# === Step 3: 步数趋势 ===
echo "=== 03: StepChart ==="
tap_center $(button_y 1)
capture "03-step-chart"
go_back

# === Step 4: 多周趋势 ===
echo "=== 04: MultiWeekTrend ==="
tap_center $(button_y 2)
capture "04-multi-week-trend"
go_back

# === Step 5: 焦虑自查 ===
echo "=== 05: AnxietySurvey ==="
tap_center $(button_y 3)
capture "05-anxiety-survey"
go_back

# === Step 6: 妈妈用药 ===
echo "=== 06: MedicationMom ==="
tap_center $(button_y 4)
capture "06-medication-mom"
go_back

# === Step 7: 爸爸用药 ===
echo "=== 07: MedicationDad ==="
tap_center $(button_y 5)
capture "07-medication-dad"
go_back

# === Step 8: 用药计划 ===
echo "=== 08: MedicationPlan ==="
tap_center $(button_y 6)
capture "08-medication-plan"
go_back

echo ""
echo "=== All screenshots captured ==="
ls -lh screenshots/*.png
