#!/usr/bin/env python3
"""Generate 3 app launcher icon options for parents-weekly-briefing-demo-app"""
from PIL import Image, ImageDraw, ImageFont
import math, os

SIZE = 512
OUT = "/Users/diygun/.openclaw/workspace/projects/parents-weekly-briefing-demo-app/icons"
os.makedirs(OUT, exist_ok=True)

def rounded_rect(draw, xy, radius, fill):
    x0, y0, x1, y1 = xy
    draw.rounded_rectangle(xy, radius=radius, fill=fill)

# --- Option 1: Shield + Family ---
def icon_shield_family():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Background
    rounded_rect(draw, (0, 0, SIZE-1, SIZE-1), 80, (52, 168, 83, 255))
    # Shield shape
    cx, cy = SIZE//2, SIZE//2 - 20
    shield = [
        (cx, cy - 140),
        (cx + 120, cy - 80),
        (cx + 120, cy + 20),
        (cx, cy + 120),
        (cx - 120, cy + 20),
        (cx - 120, cy - 80),
    ]
    draw.polygon(shield, fill=(255, 255, 255, 230))
    # Parent head
    draw.ellipse([cx-30, cy-90, cx+30, cy-30], fill=(52, 168, 83))
    # Child head
    draw.ellipse([cx+20, cy-50, cx+55, cy-10], fill=(52, 168, 83))
    # Checkmark
    draw.line([(cx-40, cy+50), (cx-10, cy+80), (cx+50, cy+20)], fill=(52, 168, 83), width=10)
    img.save(os.path.join(OUT, "opt1_shield_family.png"))
    print("✅ Option 1: shield_family")

# --- Option 2: Calendar + Heart ---
def icon_calendar_heart():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    rounded_rect(draw, (0, 0, SIZE-1, SIZE-1), 80, (25, 118, 210, 255))
    # Calendar body
    draw.rounded_rectangle([100, 100, 412, 430], radius=30, fill=(255, 255, 255, 240))
    # Calendar top bar
    draw.rounded_rectangle([100, 100, 412, 180], radius=30, fill=(244, 67, 54, 255))
    draw.rectangle([100, 150, 412, 180], fill=(244, 67, 54, 255))
    # Ring holes
    draw.ellipse([160, 70, 200, 130], fill=(25, 118, 210))
    draw.ellipse([310, 70, 350, 130], fill=(25, 118, 210))
    # Heart in center
    cx2, cy2 = 256, 310
    r = 45
    draw.ellipse([cx2-r, cy2-r+10, cx2+10, cy2+r+10], fill=(244, 67, 54))
    draw.ellipse([cx2-10, cy2-r+10, cx2+r, cy2+r+10], fill=(244, 67, 54))
    draw.polygon([(cx2-r, cy2+10), (cx2+r, cy2+10), (cx2, cy2+r+30)], fill=(244, 67, 54))
    # 7 dots (week)
    for i in range(7):
        dx = 140 + i * 38
        draw.ellipse([dx, 210, dx+20, 230], fill=(25, 118, 210))
    img.save(os.path.join(OUT, "opt2_calendar_heart.png"))
    print("✅ Option 2: calendar_heart")

# --- Option 3: Speech Bubble + Heart ---
def icon_bubble_heart():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    rounded_rect(draw, (0, 0, SIZE-1, SIZE-1), 80, (255, 152, 0, 255))
    # Speech bubble
    draw.rounded_rectangle([80, 80, 432, 350], radius=50, fill=(255, 255, 255, 240))
    # Bubble tail
    draw.polygon([(160, 350), (120, 430), (230, 350)], fill=(255, 255, 255, 240))
    # Heart inside bubble
    cx3, cy3 = 256, 220
    r3 = 55
    draw.ellipse([cx3-r3, cy3-r3, cx3+10, cy3+r3], fill=(244, 67, 54))
    draw.ellipse([cx3-10, cy3-r3, cx3+r3, cy3+r3], fill=(244, 67, 54))
    draw.polygon([(cx3-r3, cy3+5), (cx3+r3, cy3+5), (cx3, cy3+r3+35)], fill=(244, 67, 54))
    # Small text lines
    for i in range(3):
        y = 390 + i * 28
        w = 180 - i * 30
        draw.rounded_rectangle([256-w//2, y, 256+w//2, y+12], radius=6, fill=(200, 200, 200))
    img.save(os.path.join(OUT, "opt3_bubble_heart.png"))
    print("✅ Option 3: bubble_heart")

icon_shield_family()
icon_calendar_heart()
icon_bubble_heart()
print(f"\n🎉 3 个图标已生成到: {OUT}")
