#!/usr/bin/env python3
"""Parse Android UI dump and capture screenshots of each app page."""
import xml.etree.ElementTree as ET
import subprocess
import time
import re
import sys

def adb(cmd):
    """Run adb command and return output."""
    result = subprocess.run(['adb'] + cmd.split(), capture_output=True, text=True)
    return result.stdout.strip()

def capture(name):
    """Capture screenshot to screenshots/{name}.png."""
    with open(f'screenshots/{name}.png', 'wb') as f:
        r = subprocess.run(['adb', 'exec-out', 'screencap', '-p'], capture_output=True)
        f.write(r.stdout)
    size = len(r.stdout)
    print(f'Captured: {name}.png ({size} bytes)', flush=True)
    return size

def main():
    # Parse UI dump
    try:
        tree = ET.parse('screenshots/ui.xml')
    except Exception as e:
        print(f'ERROR: Cannot parse UI dump: {e}', file=sys.stderr)
        # Fallback: try hardcoded coordinates for typical 320x640 screen
        print('Using fallback hardcoded coordinates...', flush=True)
        fallback_taps = [
            (160, 400),  # Button 1 area
            (160, 500),  # Button 2 area
            (160, 600),  # Button 3 area
            (160, 700),  # Button 4 area
        ]
        pages = ['weekly-briefing', 'medication-mom', 'medication-dad', 'medication-plan']
        for i, name in enumerate(pages):
            cx, cy = fallback_taps[i]
            print(f'Clicking {name} at ({cx},{cy})', flush=True)
            subprocess.run(['adb', 'shell', 'input', 'tap', str(cx), str(cy)])
            time.sleep(5)
            capture(name)
            subprocess.run(['adb', 'shell', 'input', 'keyevent', 'KEYCODE_BACK'])
            time.sleep(3)
        return

    root = tree.getroot()
    buttons = []

    for node in root.iter('node'):
        clickable = node.get('clickable', 'false')
        bounds = node.get('bounds', '')
        text = node.get('text', '')
        desc = node.get('content-desc', '')

        if clickable == 'true' and bounds:
            m = re.match(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', bounds)
            if m:
                x1, y1, x2, y2 = map(int, m.groups())
                cx, cy = (x1 + x2) // 2, (y1 + y2) // 2
                buttons.append({
                    'cx': cx, 'cy': cy,
                    'text': text, 'desc': desc, 'bounds': bounds
                })
                print(f'Button: text="{text}" desc="{desc}" at ({cx},{cy})', flush=True)

    if not buttons:
        print('WARNING: No clickable buttons found in UI dump!', file=sys.stderr)
        print('UI dump contents:', flush=True)
        with open('screenshots/ui.xml', 'r') as f:
            print(f.read())
        return

    print(f'\nFound {len(buttons)} clickable buttons', flush=True)

    pages = ['weekly-briefing', 'medication-mom', 'medication-dad', 'medication-plan']

    for i, name in enumerate(pages):
        if i < len(buttons):
            b = buttons[i]
            print(f'\n--- {name}: clicking at ({b["cx"]},{b["cy"]}) ---', flush=True)
            subprocess.run(['adb', 'shell', 'input', 'tap', str(b['cx']), str(b['cy'])])
            time.sleep(5)
            size = capture(name)
            if size < 5000:
                print(f'WARNING: {name}.png is only {size} bytes, might be blank', flush=True)
            subprocess.run(['adb', 'shell', 'input', 'keyevent', 'KEYCODE_BACK'])
            time.sleep(3)
        else:
            print(f'WARNING: Not enough buttons for {name} (need {i+1}, have {len(buttons)})', flush=True)

    print('\n=== Screenshot capture complete ===', flush=True)

if __name__ == '__main__':
    main()
