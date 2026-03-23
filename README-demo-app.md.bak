# 父母周报 · 演示版 Android App

独立 APK，可直接安装演示，不依赖微信、不连后端。

## 安装方式

1. 将 `parents-weekly-briefing-demo-v1.apk` 传到 Android 手机
2. 设置 → 安全 → 允许安装未知来源应用
3. 点击 APK 文件安装
4. 打开后显示「父母周报」应用

## 三个演示场景

### 页面 1：主页（菜单）

三个按钮，快速进入想演示的场景。

### 页面 2：子女端 · 本周黄灯周报

- 顶部黄灯 + 一句话总结
- 3 条事实：妈用药完成率、妈步数异常、爸低活动
- 1 条行动建议
- 底部 3 条回声选项（单选高亮，不持久化）

### 页面 3：妈妈 · 用药确认（有回声）

- 大按钮「今天已吃药」（点击变绿 + "已记录"）
- 回声卡片：「最近有点担心，改天好好跟你聊聊。」

### 页面 4：爸爸 · 用药确认（无回声）

- 大按钮「状态正常」
- **没有**回声卡片

## 技术栈

- Kotlin + Jetpack Compose (Material 3)
- Navigation Compose 路由
- 纯本地假数据，无网络依赖
- 最低 Android 8.0 (API 26)

## UI 配色（与小程序对齐）

| 用途 | 色值 |
|------|------|
| 主色（顶栏/按钮） | `#F59E0B` 暖琥珀 |
| 状态正常 | `#22C55E` 暖绿 |
| 蓝色元素 | `#3B82F6` 暖蓝 |
| 黄灯背景 | `#FFFBEB` |
| 回声卡片背景 | `#F0F9FF` |

## 构建

```bash
# 需要 JDK 17 + Android SDK (platforms;android-34, build-tools;34.0.0)
./gradlew assembleDebug
# 输出: app/build/outputs/apk/debug/app-debug.apk
```
