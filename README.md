# Parents Weekly Briefing · Demo App

> **状态：2026-03 正在寻找 5–10 个愿意尝试的家庭内测。**

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="App Icon" width="120"/><br/>
  <em>Launcher icon — 扁平设计，青绿配色</em>
</p>

<p align="center">
  <strong>父母周报 Demo App</strong><br/>
  青绿扁平设计 · 纯本地演示 · 不联网 · 不收集数据
</p>

---

## 📸 Screenshots

<p align="center">
  <table>
    <tr>
      <td align="center"><b>首页</b></td>
      <td align="center"><b>周报概览</b></td>
      <td align="center"><b>步数趋势</b></td>
      <td align="center"><b>多周趋势</b></td>
    </tr>
    <tr>
      <td><img src="docs/media/screenshots/01-home.png" width="200"/></td>
      <td><img src="docs/media/screenshots/02-report.png" width="200"/></td>
      <td><img src="docs/media/screenshots/03-step-chart.png" width="200"/></td>
      <td><img src="docs/media/screenshots/04-multi-week-trend.png" width="200"/></td>
    </tr>
    <tr>
      <td align="center"><b>焦虑自查</b></td>
      <td align="center"><b>妈妈用药</b></td>
      <td align="center"><b>爸爸用药</b></td>
      <td align="center"><b>用药计划</b></td>
    </tr>
    <tr>
      <td><img src="docs/media/screenshots/05-anxiety-survey.png" width="200"/></td>
      <td><img src="docs/media/screenshots/06-medication-mom.png" width="200"/></td>
      <td><img src="docs/media/screenshots/07-medication-dad.png" width="200"/></td>
      <td><img src="docs/media/screenshots/08-medication-plan.png" width="200"/></td>
    </tr>
  </table>
</p>

> 📷 Android 模拟器截图（2026-03-31）。色板 v1.1（BrandTeal #20A080）。所有数据均为演示数据，不代表真实用户情况，也不构成任何医疗建议。

## 项目简介 / Project Overview

**中文：** 父母周报 Demo App 是一个独立的 Android 演示应用，用来展示"父母周报"的核心体验：子女每周黄灯周报、爸妈用药确认、大按钮 + 回声卡片。所有数据均为假数据，不联网。

**English:** Parents Weekly Briefing Demo App is a standalone Android APK that showcases the core experience of the main product: weekly briefing for adult children and gentle parent-side medication confirmation, using offline fake data only.

## 功能一览 / Features

- **Home**: 四个大按钮入口
- **Child · Weekly Yellow Report**: 交通灯 + 3 事实 + 1 建议 + 回声选项
- **Mom · Medication Confirm**: 大按钮 + 子女回声卡片
- **Dad · Medication Confirm**: 大按钮，无回声

## 安装方式 / Installation

### 方式一：下载 APK（推荐）

1. 从 [GitHub Releases](https://github.com/aitogether/parents-weekly-briefing-demo-app/releases) 下载最新 APK。
2. 在 Android 8.0+ 设备上，进入 设置 → 安全 → 允许安装来自未知来源的应用。
3. 安装 APK。

> ⚠️ 本 App 仅用于演示，不联网、不收集任何真实数据。
> This app is for demo purposes only — it does not connect to the internet or collect any real data.

### 方式二：从源码构建

**前置条件：**
- Android Studio（推荐 Hedgehog 2023.1+）
- JDK 17+
- Android SDK 34

**步骤：**

```bash
# 1. 克隆仓库
git clone https://github.com/aitogether/parents-weekly-briefing-demo-app.git
cd parents-weekly-briefing-demo-app

# 2. 用 Android Studio 打开项目
#    File → Open → 选择项目根目录

# 3. 等待 Gradle Sync 完成

# 4. 连接设备或启动模拟器，点击 ▶ Run
```

> 预期结果：App 成功安装到设备，显示主页（4 个大按钮入口），所有功能均为本地假数据，无需联网。

## 演示脚本 / Demo Walkthrough

1. **从主页点「子女端 · 本周黄灯周报」**，展示 绿/黄/红灯 + 3 事实 + 1 建议。
2. **回到主页 → 点「妈妈 · 用药确认」**，展示大按钮 + 子女回声。
3. **再点「爸爸 · 用药确认」**，展示只有按钮、无回声的状态。

## 配色方案

<p align="center">
  <img src="https://via.placeholder.com/60x60/20A080/ffffff?text=T" width="30"/> BrandTeal
  &nbsp;
  <img src="https://via.placeholder.com/60x60/70E090/ffffff?text=M" width="30"/> BrandMint
  &nbsp;
  <img src="https://via.placeholder.com/60x60/E84040/ffffff?text=R" width="30"/> HeartRed
</p>

色板规范 → [主项目 color-palette.md](https://github.com/aitogether/parents-weekly-briefing/blob/main/docs/ui/color-palette.md)

## 和主项目的关系 / Related Project

- **主仓库**: [aitogether/parents-weekly-briefing](https://github.com/aitogether/parents-weekly-briefing)
- This demo app is a simplified offline companion to the main project (backend + WeChat Mini Program).
- 本 demo 是主项目（后端 + 微信小程序）的离线简化演示版。

---

## Commercial use & branding

### Personal / non-commercial use

This project is open source and welcomes personal and non-commercial use. You can use, modify, and deploy it freely under the [CC BY-NC 4.0](LICENSE) license. We appreciate attribution, but it is not required for private use.

### Commercial use

If you plan to integrate this project into a paid product or service, or deploy it as part of a commercial offering (e.g. SaaS for caregivers, hospital / clinic deployments, insurance / eldercare bundles), **please contact the author to discuss a commercial license or revenue-sharing agreement**.

### Branding & naming

The names 「父母周报」 and "Parents Weekly Briefing", as well as related logos / icons, are reserved as project branding. Commercial use of these names or logos requires prior written permission.

---

## License

[CC BY-NC 4.0](LICENSE) — 个人非商业使用免费，商业使用请联系作者。

*个人随便用，赚钱要谈。*
