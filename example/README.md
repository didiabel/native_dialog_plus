# native_dialog_test

A flutter minimalistic plugin to show native Dialogs!!

## Description

#### Android

Native Dialog Plus uses the native UI on each platform to show alert and confirm dialogs.

**IMPORTANT**

Due to framework limitations Android is limited to the maximum of 3 actions one of each NativeDialogPlusActionStyle style
therefore its limited to one defaultStyle, cancel and destructive each, the order of the actions in the list does not change the position in the dialog.

#### IOS

iOS has no limit on the number of actions.

Buttons are disabled if no callback is passed on onPressed.

## Usage

### Alert dialog

```dart
import 'package:native_dialog_plus/native_dialog_plus.dart';

NativeDialogPlus(
    actions: [
    NativeDialogPlusAction(
            text: 'Now',
            style: NativeDialogPlusActionStyle.destructive,
        ),
        NativeDialogPlusAction(
            text: "BZ'H",
            onPressed: () {
                print("Moshiach vibes");
            },
            style: NativeDialogPlusActionStyle.defaultStyle,
        ),
    ],
    title: 'This is a test dialog',
    message: 'Moshiach NOW',
).show();
```

#### Android

<img alt="android-alert" src="https://raw.githubusercontent.com/didiabel/native_dialog_plus/dev/assets/android.gif" height="480">

#### iOS

<img alt="ios-alert" src="https://raw.githubusercontent.com/didiabel/native_dialog_plus/dev/assets/iOS.gif" height="480">

#### Web

<img alt="web-alert" src="https://raw.githubusercontent.com/didiabel/native_dialog_plus/dev/assets/web.gif" height="480">
