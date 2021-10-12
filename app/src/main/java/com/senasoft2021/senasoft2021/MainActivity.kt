package com.senasoft2021.senasoft2021

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.senasoft2021.senasoft2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idBtnLoginHuawei.setOnClickListener { loginHuawei() }
    }

    private fun loginHuawei() {
        var myuthParam= HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setId()
            .setAccessToken()
            .setIdToken()
            .setUid()
            .setEmail()
            .setProfile()
            .createParams()
        var myUthManager= HuaweiIdAuthManager.getService(this, myuthParam)
        startActivityForResult(myUthManager.signInIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100){
            if (resultCode==Activity.RESULT_CANCELED){
                Toast.makeText(this, "login cancelado", Toast.LENGTH_SHORT).show()
            }else if (resultCode==Activity.RESULT_OK){
                var taskLogin=HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (taskLogin.isSuccessful){
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, InicioActivity::class.java))
                }
            }
        }
    }
}