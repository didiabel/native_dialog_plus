#import "NativeDialogPlusPlugin.h"
#if __has_include(<native_dialog_plus/native_dialog_plus-Swift.h>)
#import <native_dialog_plus/native_dialog_plus-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "native_dialog_plus-Swift.h"
#endif

@implementation NativeDialogPlusPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNativeDialogPlusPlugin registerWithRegistrar:registrar];
}
@end