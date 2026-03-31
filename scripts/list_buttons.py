#!/usr/bin/env python3
"""List all clickable nodes from UI dump."""
import xml.etree.ElementTree as ET
import re

tree = ET.parse('screenshots/ui.xml')
for n in tree.getroot().iter('node'):
    t = n.get('text', '')
    d = n.get('content-desc', '')
    clickable = n.get('clickable', 'false')
    bounds = n.get('bounds', '')
    rid = n.get('resource-id', '')
    if clickable == 'true' and (t or d):
        m = re.match(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', bounds)
        if m:
            x1, y1, x2, y2 = map(int, m.groups())
            cx, cy = (x1 + x2) // 2, (y1 + y2) // 2
            print(f'  [{cx},{cy}] text="{t}" desc="{d}" id="{rid}"')
