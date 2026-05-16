<img width="440" height="980" alt="Screenshot_20260516_173900" src="https://github.com/user-attachments/assets/5a1a171b-8c27-4507-8647-0ad99bc810d1" />
<img width="443" height="988" alt="Screenshot_20260516_173921" src="https://github.com/user-attachments/assets/db597b9a-328a-483f-8e55-ed95146cf183" />

# BluetoothJammer (Refactored UI / Core Improvements)

A heavily refactored version of the original Bluetooth testing project, focused on **UI modernization, architecture cleanup, and stability improvements**.

This fork introduces a full UI rewrite using Jetpack Compose, improved lifecycle management, and more reliable Bluetooth scanning and session handling.

---

## ✨ Key Improvements

### 🎨 UI / UX Overhaul
- Migrated from XML layouts to **Jetpack Compose**
- Custom cyberpunk-inspired theme (neon cyan/magenta)
- New reusable components:
  - `CyberButton` (neon styled buttons)
  - `GlitchText` (animated glitch effect titles)
  - `CyberCard` (glass/neon containers)
- Improved layout responsiveness and visual consistency

### ⚙️ Core Architecture Fixes
- Introduced centralized `AttackManager`
- Proper lifecycle handling for background threads
- Guaranteed cleanup on stop / app close
- Reduced risk of orphaned background processes

### 📡 Bluetooth System Improvements
- Rewritten device discovery system
- Real-time scanning updates without UI blocking
- Better handling of paired + nearby devices

### 📊 Logging & Debugging
- Real-time log console stream
- Structured log categories:
  - Thread lifecycle events
  - Connection state updates
  - Data transmission tracking
  - Retry and cleanup events

### 🎚️ New Features
- Adjustable intensity slider (concurrency control)
- Live session monitoring
- Updated app icon and branding assets

---

## 🧪 Purpose

This project is intended for:
- Bluetooth system testing
- Android concurrency and lifecycle experimentation
- UI/UX modernization experiments with Jetpack Compose
- Debugging and stress-testing device connectivity behavior

⚠️ This project is not intended for real-world interference or malicious use.

---

## 🛠️ Tech Stack

- Kotlin
- Android SDK
- Jetpack Compose
- Coroutines / Threads
- Bluetooth APIs (Android)

---

## 🚀 Build & Run

1. Clone the repository
```bash
git clone https://github.com/PIXELQUADRO07/BluetoothJammer.git
```

2. Open in Android Studio

3. Sync Gradle

4. Run on a physical Android device (recommended for Bluetooth features)

## 📱 Requirements

- Android 8.0+
- Bluetooth permissions enabled
- Physical device recommended (emulators have limited Bluetooth support)

---

## 🤝 Contribution

Contributions are welcome!

If you'd like to contribute:

- Open an issue describing the improvement or bug
- Or submit a pull request with a clear description of changes

---

## 📄 License

Inherits the original project license (if applicable).
Please refer to the original repository for licensing terms.

---

## ⚠️ Disclaimer

This project is provided for educational and research purposes only.

The authors are not responsible for any misuse of this software.

---

**Last Updated:** 2026-05-16
