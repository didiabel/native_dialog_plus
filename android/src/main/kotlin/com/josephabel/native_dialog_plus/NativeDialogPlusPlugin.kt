package com.josephabel.native_dialog_plus

import com.josephabel.native_dialog_plus.R
import android.app.Activity
import android.app.AlertDialog
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.content.DialogInterface

class NativeDialogPlusPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel : MethodChannel
    private var activity: Activity? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "native_dialog_plus")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "showDialog" -> {
                
                val title = call.argument<String?>("title") ?: ""
                val message = call.argument<String>("message") ?: ""
                val buttons = call.argument<List<Map<String, Any>>>("actions")

                if (buttons != null) {
                    val buttonConfigs = buttons.mapIndexed { index, button ->
                        val text = button["text"] as String
                        val style = when(button["style"] as String) {
                            "DEFAULT" -> NativeDialogPlusActionStyle.DEFAULT
                            "CANCEL" -> NativeDialogPlusActionStyle.CANCEL
                            "DESTRUCTIVE" -> NativeDialogPlusActionStyle.DESTRUCTIVE
                            else -> NativeDialogPlusActionStyle.DEFAULT
                        }
                        NativeDialogPlusAction(text, style) {
                            result.success(index)
                        }
                    }
                    showDialog(title, message, buttonConfigs, result)
                } else {
                    result.error("INVALID_ARGUMENT", "Buttons argument is missing or invalid", null)
                }                
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    private fun showDialog(
        title: String,
        message: String,
        buttons: List<NativeDialogPlusAction>,
        result: Result
    ) {
        ContextThemeWrapper ctw = new ContextThemeWrapper(TestActivity.this, R.style.MyAlertDialogStyle);

        // Create AlertDialog.Builder
        val builder = AlertDialog.Builder(ctw)
            .setTitle(title)
            .setMessage(message)
    
        // Add buttons to the dialog
        buttons.forEachIndexed { index, action ->
                when (action.style) {
                    NativeDialogPlusActionStyle.DEFAULT -> builder.setButton(action.text) { dialog, _ ->
                        action.onPressed?.invoke()
                        dialog.dismiss()
                    }
                    NativeDialogPlusActionStyle.CANCEL -> builder.setButton(action.text) { dialog, _ ->
                        action.onPressed?.invoke()
                        dialog.dismiss()
                    }
                    NativeDialogPlusActionStyle.DESTRUCTIVE -> builder.setButton(action.text) { dialog, _ ->
                        action.onPressed?.invoke()
                        dialog.dismiss()
                    }
                }
        }
    
        // Show the dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }
    
    data class NativeDialogPlusAction(val text: String, val style: NativeDialogPlusActionStyle, val onPressed: () -> Unit)

    enum class NativeDialogPlusActionStyle {
        DEFAULT,
        CANCEL,
        DESTRUCTIVE
    }    
}
