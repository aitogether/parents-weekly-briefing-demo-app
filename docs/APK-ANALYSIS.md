# ParentsWeeklyBriefing Demo App — APK 内容分析报告

> 分析日期：2026-03-24  
> APK 文件：`parents-weekly-briefing-demo-v1.apk` (14.1 MB)  
> GitHub Release：[v1.0 — 首个演示版 APK](https://github.com/aitogether/parents-weekly-briefing-demo-app/releases/tag/v1)

## ⚠️ 测试限制

**本地无 Android SDK / 模拟器环境**，无法进行安装和截屏测试。

- `emulator`、`adb` 均未找到
- `ANDROID_HOME` / `ANDROID_SDK_ROOT` 未设置
- `~/Library/Android/sdk` 不存在
- GitHub Actions CI 有最新构建产物但 `gh` CLI 未认证

建议方案：
1. **安装 Android SDK**：`brew install --cask android-commandlinetools`，然后 `sdkmanager` 安装模拟器镜像
2. **使用 GitHub Actions**：配置 `gh auth login` 后下载 CI 构建的 APK 和截图
3. **在线 Android 模拟器**：如 BrowserStack、Sauce Labs 等

---

## APK 基本信息

| 属性 | 值 |
|------|-----|
| Package | `com.aitogether.parentswb` |
| App Name | ParentsWeeklyBriefing (父母周报) |
| Version | v1.0 |
| Build Tool | AGP 8.2.2 (Gradle) |
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material3 |
| DEX 文件 | 6 个 (classes.dex ~ 41MB, classes6.dex ~ 7.6MB) |
| 总文件数 | 153 |
| 解压大小 | 50 MB |

## 应用结构

### 页面/屏幕 (Navigation Routes)

| 路由 | 屏幕组件 | 说明 |
|------|----------|------|
| `home` | `HomeScreen` | 主页 — 选择演示场景 |
| `report` | `ReportScreen` | 子女端 · 本周黄灯周报 |
| `medication/mom` | `MedicationScreen` | 妈妈 · 用药确认 |
| `medication/dad` | `MedicationScreen` | 爸爸 · 用药确认 |
| `medication/plan` | `MedicationPlanScreen` | 子女端 · 添加用药计划 |

### 核心源文件

| 文件 | 说明 |
|------|------|
| `MainActivity.kt` | 入口 Activity，含隐私弹窗 (PrivacyDialog) |
| `AppNavHost.kt` | 导航控制，定义 5 个 Screen 路由 |
| `HomeScreen.kt` | 首页，4 个功能入口按钮 |
| `ReportScreen.kt` | 周报详情页（含异常摘要与回声） |
| `MedicationScreen.kt` | 用药确认页面（参数化：爸爸/妈妈） |
| `MedicationPlanScreen.kt` | 用药计划页（含 PlanPreview, TimeChip 组件） |

### UI 主题

- 主色调：BrandTeal（青色）
- 辅助色：WarmGreen（绿色，妈妈）、WarmBlue（蓝色，爸爸）、Purple600（紫色，用药计划）
- 背景/文字：White、DarkGray、MediumGray
- 使用 Material3 + Material Icons Extended

## 主页功能按钮

1. **📋 子女端 · 本周黄灯周报** — 查看本周异常摘要与回声
2. **💊 妈妈 · 用药确认** — 看到子女的一句话（有回声）
3. **💚 爸爸 · 用药确认** — 暂未收到回声（无回声状态）
4. **📝 子女端 · 添加用药计划** — 演示为父母设定用药计划

## 资源文件

- App 图标：`ic_launcher.png` (192×192, xxxhdpi) — 已保存到 `docs/media/app-icon.png`
- 启动图标：多个 dpi 版本 (mdpi → xxxhdpi)
- 无自定义布局 XML（Compose 声明式 UI，布局在代码中）

## 技术栈

- **AndroidX**：Activity Compose, Navigation Compose, Lifecycle
- **Compose**：Foundation, Material3, Animation, UI Graphics/Text
- **Kotlin** Coroutines
- 6 个 DEX 文件（可能包含大量 Compose 编译产物）

---

## 待办：截屏计划（需要 Android 环境）

如果有了 Android SDK / 模拟器，截屏顺序：

1. 启动 App → 主页 (`HomeScreen`)
2. 点击"📋 子女端 · 本周黄灯周报" → 周报页 (`ReportScreen`)
3. 返回 → 点击"💊 妈妈 · 用药确认" → 妈妈用药页 (`MedicationScreen`, mom)
4. 返回 → 点击"💚 爸爸 · 用药确认" → 爸爸用药页 (`MedicationScreen`, dad)
5. 返回 → 点击"📝 子女端 · 添加用药计划" → 用药计划页 (`MedicationPlanScreen`)

截图保存目录：`projects/parents-weekly-briefing-demo-app/docs/media/screenshots/`
