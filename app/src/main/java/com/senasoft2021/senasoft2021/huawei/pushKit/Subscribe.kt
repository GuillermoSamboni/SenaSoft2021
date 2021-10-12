package com.senasoft2021.senasoft2021.huawei.pushKit

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.huawei.hms.push.HmsMessaging

class Subscribe() {
    fun subscribe(context: Context, topic: String) {
        try {
            HmsMessaging.getInstance(context)
                .subscribe(topic)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "suscrito ad :$topic", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "no suscrito ad :$topic", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Log.d("ErrorSub", e.toString())
        }
    }
}