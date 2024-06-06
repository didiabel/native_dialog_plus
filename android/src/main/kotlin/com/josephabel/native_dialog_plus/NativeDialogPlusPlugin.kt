package com.josephabel.native_dialog_plus

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.NonNull
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


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
                        println(button["style"])
                        val text = button["text"] as String
                        val style = button["style"] as Int
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

        val builder = AlertDialog.Builder(activity ?: throw NullPointerException(), R.style.NativeDialogStyle)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)

        // Add buttons to the dialog
        buttons.forEachIndexed { index, action ->
            val listener = DialogInterface.OnClickListener { dialog, _ -> result.success(index) }
            if (listener != null) {
                when (action.style) {
                    0 -> builder.setPositiveButton(action.text, listener)
                    1 -> builder.setNeutralButton(action.text, listener)
                    2 -> builder.setNegativeButton(action.text, listener)
                }
            }
        }

        val alertDialog = builder.create()

        val back = ColorDrawable(Color.WHITE)
        val inset = InsetDrawable(back, 20, 20, 20, 20)
        alertDialog.window?.setBackgroundDrawable(inset)

        alertDialog.show()

        // for future implementations this is how to set button colors
        // import
        // import android.graphics.Color
        // import android.widget.Button
        
        // val negativeButton: Button? = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        // negativeButton?.setTextColor(Color.RED)

        // val positiveButton: Button? = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        // positiveButton?.setTextColor(Color.GREEN)

        // val neutralButton: Button? = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        // neutralButton?.setTextColor(Color.BLUE)
    }

    data class NativeDialogPlusAction(val text: String, val style: Int, val onPressed: () -> Unit)

}