import Cocoa
import FlutterMacOS

public class NativeDialogPlusPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(
      name: "native_dialog_plus", binaryMessenger: registrar.messenger)
    let instance = NativeDialogPlusPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "showDialog":
      self.showDialog(call, result)
    default:
      result(FlutterMethodNotImplemented)
    }
  }

  private var window: NSWindow? {
    return NSApplication.shared.windows.first
  }

  private var unavailableError: FlutterError {
    return FlutterError(code: "UNAVAILABLE", message: "Native alert is unavailable", details: nil)
  }

  private var invalidStyleError: FlutterError {
    return FlutterError(
      code: "INVALID_STYLE", message: "Given index for style is invalid", details: nil)
  }

  private func indexToAlertStyle(_ index: Int) -> NSAlert.Style? {
    switch index {
    case 0:
      return .warning
    case 1:
      return .informational
    case 2:
      return .critical
    default:
      return nil
    }
  }

  private func showDialog(_ call: FlutterMethodCall, _ result: @escaping FlutterResult) {
    let args = call.arguments as! NSDictionary
    let title = args.value(forKey: "title") as? String ?? ""
    let message = args.value(forKey: "message") as? String ?? ""
    let styleIndex = args.value(forKey: "style") as! Int

    guard let alertStyle = indexToAlertStyle(styleIndex) else {
      result(invalidStyleError)
      return
    }

    let alert = NSAlert()
    alert.messageText = title
    alert.informativeText = message
    alert.alertStyle = alertStyle

    let actions = args.value(forKey: "actions") as! [NSDictionary]

    for (index, action) in actions.enumerated() {
      let buttonTitle = action.value(forKey: "text") as! String
      let isEnabled = action.value(forKey: "enabled") as! Bool
      let style = action.value(forKey: "style") as? Int ?? 0

      let button = alert.addButton(withTitle: buttonTitle)
      button.isEnabled = isEnabled

      if #available(macOS 11.0, *), style == 2 {
        button.hasDestructiveAction = true
      }
    }

    guard let window = self.window else {
      result(unavailableError)
      return
    }

    alert.beginSheetModal(for: window) { response in
      let selectedIndex =
        Int(response.rawValue) - Int(NSApplication.ModalResponse.alertFirstButtonReturn.rawValue)
      if selectedIndex >= 0 && selectedIndex < actions.count {
        result(selectedIndex)  // Return the index of the clicked button
      } else {
        result(
          FlutterError(
            code: "BUTTON_SELECTION_ERROR", message: "Invalid button index", details: nil))
      }
    }
  }
}
