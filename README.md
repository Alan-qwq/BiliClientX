<div align="center">

# 哔哩终端 X

**轻量的第三方 B 站 Android 客户端**

[![GitHub release](https://img.shields.io/github/v/release/Alan-qwq/BiliClientX)](https://github.com/Alan-qwq/BiliClientX/releases/latest)
[![License: GPL-3.0](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-4.0.4%2B-green.svg)](https://developer.android.com)

[GitHub](https://github.com/Alan-qwq/BiliClientX) · [Gitee](https://gitee.com/alanqwq/BiliClientX) · [官网](https://bili.alanqwq.top)

</div>

## 介绍

**哔哩终端 X** 是基于 [哔哩终端（BiliClient）](https://gitee.com/RobinNotBad/BiliClient) v2.9.7 的二次开发分支，由 Alan 独立维护。

原项目使用 Java + XML 开发，**极其轻量**，最低支持 **Android 4.0.4**。本分支延续了这一设计理念，保持轻量、优先保证**可用性**与**流畅性**。

> **注意**：本仓库是独立维护的分支版本，所有代码更新、问题修复均由 Alan 负责，与原作者无关。

## 特性

- 📺 视频播放（支持弹幕）
- 🔍 搜索视频、用户
- 📝 动态查看
- 💬 评论浏览
- 📥 视频/图片下载
- 🔐 扫码登录
- 🎨 Material Design 风格界面

## 与原版的关系

| 项目 | 说明 |
|------|------|
| 原项目 | [哔哩终端（BiliClient）](https://gitee.com/RobinNotBad/BiliClient) |
| 原作者 | RobinNotBad |
| 原包名 | `com.RobinNotBad.BiliClient` |
| 原最终版本 | v2.9.7（已停更） |
| 本分支包名 | `com.Alan.BiliClientX` |
| 本分支维护者 | Alan |

## 技术栈

| 依赖 | 说明 |
|------|------|
| OkHttp 3.12.1 | 网络请求 |
| Glide 4.13.2 | 图片加载 |
| Jsoup 1.10.2 | HTML 解析 |
| IJKPlayer | 视频播放 |
| DanmakuFlameMaster | 弹幕渲染 |
| ZXing 3.3.0 | 二维码扫描 |
| EventBus 3.2.0 | 事件总线 |
| Brotli | 数据压缩 |

## 构建

### 环境要求

- **JDK 17**（必须，JDK 21 不兼容 Gradle 7.4）
- **Android SDK**（compileSdk 33）
- **Gradle 7.4**（通过 wrapper 自动下载）

### 编译步骤

```bash
# 1. 设置 JAVA_HOME 为 JDK 17
export JAVA_HOME="/path/to/jdk-17"

# 2. 创建 local.properties，指定 Android SDK 路径
echo "sdk.dir=/path/to/android/sdk" > local.properties

# 3. 编译 Debug 包
./gradlew :app:assembleDebug --no-daemon
```

编译产物位于 `app/build/outputs/apk/debug/`。

### 注意事项

- 路径中**不能包含中文**等非 ASCII 字符，否则需在 `gradle.properties` 中添加 `android.overridePathCheck=true`
- 必须使用 **JDK 17**，高版本会导致 `Unsupported class file major version` 错误

## 项目结构

```
BiliClientX/
├── app/                    # 主应用模块
│   ├── libs/               # Native .so 库
│   └── src/main/
│       ├── java/           # Java 源码
│       ├── res/            # 资源文件
│       └── AndroidManifest.xml
├── DanmakuFlameMaster/     # 弹幕引擎库
├── ijkplayer-java/         # 视频播放器库
├── brotlij/                # Brotli 压缩库
├── gradle/                 # Gradle Wrapper
├── build.gradle            # 根构建配置
├── settings.gradle         # 模块配置
└── LICENSE                 # GPL-3.0 协议
```

## 联系

- QQ 交流群：[待补充]
- 备用群：[待补充]

## 致谢

- [RobinNotBad](https://gitee.com/RobinNotBad) — 哔哩终端原作者
- [WearBili](https://github.com/SpaceXC/WearBili) / [Re:WearBili](https://github.com/SpaceXC/Re-WearBili) — UI 参考
- [腕上哔哩](https://github.com/luern0313/WristBilibili) — 部分代码参考
- [BAC Document](https://socialsisteryi.github.io/bilibili-API-collect) — API 文档
- [PiliPlus](https://github.com/pili-player/PiliPlus) — 部分代码参考

## 开发

欢迎提交 Issue 和 Pull Request。

> `develop` 分支用于在线开发，获取到的为最新源码，但可能会存在未修复的问题。

## 协议

本项目基于 [GNU GPL-3.0](LICENSE) 协议开源。
