package com.senasoft2021.senasoft2021.huawei.pushKit

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.huawei.hms.push.HmsMessaging
import kotlin.concurrent.thread

class SubscribeToken() {
    fun subscribe(context: Context, topic: String?) {

            try {
                HmsMessaging.getInstance(context)
                    .subscribe(topic)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Toast.makeText(context, "suscrito ad :$topic", Toast.LENGTH_SHORT).show()
                            var alert = AlertDialog.Builder(context)
                            alert.setMessage("suscrito ad :$topic")
                                .setTitle("Suscripcion")
                            alert.setCancelable(false)
                                .setPositiveButton("Aceptar") { _, _ ->
                                    alert.setCancelable(true)
                                }.create().show()

                        } else {
                            Toast.makeText(context, "no suscrito ad :$topic", Toast.LENGTH_SHORT)
                                .show()
                            var alert = AlertDialog.Builder(context)
                            alert.setMessage("No suscrito ad :$topic")
                                .setTitle("Suscripcion fallida")
                            alert.setCancelable(false)
                                .setPositiveButton("Aceptar") { _, _ ->
                                    alert.setCancelable(true)
                                }.create().show()
                        }
                    }
            } catch (e: Exception) {
                Log.d("ErrorSub", e.toString())
            }
        }

}