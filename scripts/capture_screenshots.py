#!/usr/bin/env python3
"""Parse Android UI dump and capture screenshots of each app page."""
import xml.etree.ElementTree as ET
import subprocess
import time
import re
import sys

def adb_shell(cmd):
    """Run adb shell command."""
    subprocess.run(['adb', 'shell'] + cmd.split(), capture_output=True)

def capture(name):
    """Capture screenshot to screenshots/{name}.png."""
    with open(f'screenshots/{name}.png', 'wb') as f:
        r = subprocess.run(['adb', 'exec-out', 'screencap', '-p'], capture_output=True)
        f.write(r.stdout)
    size = len(r.stdout)
    print(f'Captured: {name}.png ({size} bytes)', flush=True)
    return size

def dump_ui(filename):
    """Dump UI hierarchy and pull to local file."""
    adb_shell(f'uiautomator dump /sdcard/{filename}')
    subprocess.run(['adb', 'pull', f'/sdcard/{filename}', f'screenshots/{filename}'], capture_output=True)

def get_clickable_buttons(xml_file):
    """Parse UI dump and return list of clickable buttons."""
    try:
        tree = ET.parse(xml_file)
    except Exception as e:
        print(f'ERROR parsing {xml_file}: {e}', file=sys.stderr)
        return []

    root = tree.getroot()
    buttons = []
    for node in root.iter('node'):
        clickable = node.get('clickable', 'false')
        bounds = node.get('bounds', '')
        text = node.get('text', '')
        res_id = node.get('resource-id', '')
        desc = node.get('content-desc', '')
        if clickable == 'true' and bounds:
            m = re.match(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', bounds)
            if m:
                x1, y1, x2, y2 = map(int, m.groups())
                cx, cy = (x1 + x2) // 2, (y1 + y2) // 2
                buttons.append({
                    'cx': cx, 'cy': cy,
                    'text': text, 'desc': desc, 'res_id': res_id, 'bounds': bounds
                })
    return buttons

def tap(cx, cy):
    """Tap at coordinates."""
    subprocess.run(['adb', 'shell', 'input', 'tap', str(cx), str(cy)])
    time.sleep(2)

def go_back():
    """Press back button."""
    subprocess.run(['adb', 'shell', 'input', 'keyevent', 'KEYCODE_BACK'])
    time.sleep(2)

def main():
    print("=== Step 1: Handle privacy dialog ===", flush=True)

    # The home page shows a privacy dialog first. Dismiss it.
    dump_ui('ui_initial.xml')
    buttons = get_clickable_buttons('screenshots/ui_initial.xml')

    print(f'Found {len(buttons)} clickable elements on initial screen', flush=True)
    for b in buttons:
        print(f'  Button: text="{b["text"]}" desc="{b["desc"]}" at ({b["cx"]},{b["cy"]})', flush=True)

    # Look for "我知道了" (I understand) button and click it
    dismissed = False
    for b in buttons:
        if '我知道了' in b['text'] or '我知道了' in b['desc']:
            print(f'Dismissing privacy dialog at ({b["cx"]},{b["cy"]})', flush=True)
            tap(b['cx'], b['cy'])
            dismissed = True
            break

    if not dismissed and buttons:
        # Click the first button as fallback (likely the dismiss button)
        print(f'No "我知道了" found, clicking first button at ({buttons[0]["cx"]},{buttons[0]["cy"]})', flush=True)
        tap(buttons[0]['cx'], buttons[0]['cy'])

    time.sleep(3)  # Wait for home page to load

    print("\n=== Step 2: Capture HOME page ===", flush=True)
    dump_ui('ui_home.xml')
    capture('home')

    # Now get the 4 main navigation buttons
    buttons = get_clickable_buttons('screenshots/ui_home.xml')
    print(f'\nFound {len(buttons)} clickable elements on home page', flush=True)
    for b in buttons:
        print(f'  Button: text="{b["text"]}" desc="{b["desc"]}" res_id="{b["res_id"]}" at ({b["cx"]},{b["cy"]}) bounds={b["bounds"]}', flush=True)

    # If still no 4 buttons, try clicking anywhere in the grid area to find them
    if len(buttons) < 2:
        print('Not enough buttons found. Trying to tap grid area coordinates...', flush=True)
        # For a 320x640 screen with content area [0,114][320,550]:
        # 2x2 grid with some padding
        # Typical grid positions
        grid_positions = [
            (80, 250, 'top-left'),
            (240, 250, 'top-right'),
            (80, 420, 'bottom-left'),
            (240, 420, 'bottom-right'),
        ]

        pages = ['weekly-briefing', 'medication-mom', 'medication-dad', 'medication-plan']

        for i, (cx, cy, label) in enumerate(grid_positions):
            name = pages[i]
            print(f'\n--- {name}: tapping {label} at ({cx},{cy}) ---', flush=True)
            tap(cx, cy)
            time.sleep(3)
            size = capture(name)
            if size < 5000:
                print(f'WARNING: {name}.png is only {size} bytes, might be blank or same as home', flush=True)
            go_back()
            time.sleep(2)
    else:
        # Navigate to each page using found buttons
        pages = ['weekly-briefing', 'medication-mom', 'medication-dad', 'medication-plan']
        for i, name in enumerate(pages):
            if i < len(buttons):
                b = buttons[i]
                print(f'\n--- {name}: clicking at ({b["cx"]},{b["cy"]}) ---', flush=True)
                tap(b['cx'], b['cy'])
                time.sleep(3)
                size = capture(name)
                if size < 5000:
                    print(f'WARNING: {name}.png is only {size} bytes', flush=True)
                go_back()
                time.sleep(2)
            else:
                print(f'WARNING: Not enough buttons for {name}', flush=True)

    print('\n=== Screenshot capture complete ===', flush=True)
    print('Files:', flush=True)
    subprocess.run(['ls', '-la', 'screenshots/'])

if __name__ == '__main__':
    main()
