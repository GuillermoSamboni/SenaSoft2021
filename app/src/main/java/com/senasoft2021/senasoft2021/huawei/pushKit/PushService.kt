package com.senasoft2021.senasoft2021.huawei.pushKit

import android.content.ComponentCallbacks
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.HmsMessaging
import com.huawei.hms.push.RemoteMessage
import kotlin.concurrent.thread

class PushService:HmsMessageService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.let {
            Log.d("Mensaje recivido", "mesage")
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
            Log.d("newToken","newToken")
    }

}
class GetTokenPushService(){
    var handler:Handler= Handler(Looper.getMainLooper())
    fun getToken(context: Context, callbacks: (String)->Unit){
        thread {
            try {
                var appId=AGConnectServicesConfig.fromContext(context).getString("client/app_id")
                var token=HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                handler.post { callbacks(token) }
            }catch (e:Exception){
                Log.d("Error", e.toString())
            }
        }
    }
}