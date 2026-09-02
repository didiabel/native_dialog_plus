## 0.0.1

- TODO: Describe initial release.

## 1.0.5

- Added namespace

## 1.0.6

- Added compileOptions and kotlinOptions for jvmTarget 1.8

## 1.0.7

- Added Action sheet for Android

## 1.0.9

- Updated compileSdkVersion to 31

## 1.1.0

-- Added wasm compatibility

## 1.1.1

-- Fix Dialogs not working in Macos

## 1.2.0

- [iOS] Working Swift Package Manager support: `Package.swift` moved to `ios/native_dialog_plus/Package.swift` with the `FlutterFramework` dependency, matching the official Flutter SPM plugin template (thanks @sunweiyang #20 and @nasikhunamin #19)
- [iOS] Plugin is now pure Swift (Objective-C shim removed); minimum iOS version raised to 12.0
- Minimum Dart SDK raised to 3.6.0

## 1.1.2

- [Android] Support dismissal by tapping outside the dialog via new `cancelable` parameter on `NativeDialogPlus` (defaults to `true`)
- [Android] Destructive buttons now render in red; default buttons render in blue
- [Android] Action sheet destructive buttons now render in red
- [iOS] Added Swift Package Manager (SPM) support
