<img width="440" height="980" alt="Screenshot_20260516_173900" src="https://github.com/user-attachments/assets/5a1a171b-8c27-4507-8647-0ad99bc810d1" />
<img width="443" height="988" alt="Screenshot_20260516_173921" src="https://github.com/user-attachments/assets/db597b9a-328a-483f-8e55-ed95146cf183" />

# BluetoothJammer 📱

> A simple Bluetooth jammer/DoS tool for Android. Built with Kotlin.
> 
> ⚠️ **WIP - Work in Progress**

## 📋 Overview

BluetoothJammer is an Android application designed to test Bluetooth device vulnerabilities through interference and denial-of-service (DoS) attacks. This educational tool helps understand Bluetooth security mechanisms.

**Disclaimer:** Use only on devices you own or have explicit permission to test. Unauthorized access to computer systems is illegal.

## ✨ Features

- 🎨 Material UI Design
- 🔄 Multi-threaded Attack Engine
- 🔀 Auto UUID Randomization
- 📊 Real-time Activity Logging
- ⚡ Optimized Attack Threads
- 🎛️ Configurable Attack Options

## 🖼️ Preview

<table style="padding:10px">
  <tr>
    <td>
        <img src="./assets/attack.png" alt="Attack Interface">
    </td>
  </tr>
</table>

## 🚀 Getting Started

### Prerequisites
- Android SDK 24+
- Kotlin 1.x
- Gradle

### Installation

1. Clone the repository:
```bash
git clone https://github.com/PIXELQUADRO07/BluetoothJammer.git
cd BluetoothJammer
```

2. Build the project:
```bash
gradle build
```

3. Install on device:
```bash
gradle installDebug
```

## 📝 TODO

### Completed ✅
- [x] Material UI
- [x] Thread Option
- [x] Log Switch
- [x] Auto randomize UUID
- [x] Optimize Attack Thread

### In Progress / Bugs 🐛
- [ ] Devices List (bug)
- [ ] Start/Stop button (known issue: thread not properly killed on stop, requires app restart)

## 🤝 Special Thanks

- ChatGPT-4o for development assistance

## 📄 License

This project is provided as-is for educational purposes only.

---

**Last Updated:** 2026-05-16
