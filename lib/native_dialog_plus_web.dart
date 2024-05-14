import 'dart:async';
import 'dart:html' as html;
import 'package:flutter/services.dart';
import 'package:flutter_web_plugins/flutter_web_plugins.dart';

class NativeDialogPlusWeb {
  static void registerWith(Registrar registrar) {
    final channel = MethodChannel(
      'native_dialog_plus',
      const StandardMethodCodec(),
      registrar,
    );

    final pluginInstance = NativeDialogPlusWeb();
    channel.setMethodCallHandler(pluginInstance.handleMethodCall);
  }

  Future<dynamic> handleMethodCall(MethodCall call) async {
    switch (call.method) {
      case 'showDialog':
        final message = call.arguments['message'];
        return _alert(message);
      default:
        throw PlatformException(
          code: 'Unimplemented',
          details:
              "native_dialog_plus for web doesn't implement '${call.method}'",
        );
    }
  }

  Future<void> _alert(String message) async {
    html.window.alert(message);
    return Future.value();
  }
}
