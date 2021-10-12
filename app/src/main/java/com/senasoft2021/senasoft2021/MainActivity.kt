package com.senasoft2021.senasoft2021

import android.content.Intent
import android.app.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityMainBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.ui.login.user.RegisterActivity
import com.senasoft2021.senasoft2021.validations.Validations
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.senasoft2021.senasoft2021.ui.login.admin.LoginAdminActivity


class MainActivity : AppCompatActivity() {


    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnLoginRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.idBtnLoginGoLoginAdmin.setOnClickListener {
            startActivity(Intent(this, LoginAdminActivity::class.java))
        }

        binding.idBtnLogin.setOnClickListener { loginUser()}

        binding.idBtnLoginHuawei.setOnClickListener { loginHuawei() }

    }

    /**
     * loguear a un usuario previamente registrado en la bd
     */
    private fun loginUser() {

        //verificar que los campos no esten vacios
        if(validarCampos()){
            this.showToast("Los campos no pueden estar vacios")
            return
        }


        val name=binding.idTxtLoginName.text.toString().trim()
        val pass=binding.idTxtLoginPass.text.toString().trim()

        if(RoomDatabaseClient.loginUSer(name,pass,this)){
            startActivity(Intent(this,InicioActivity::class.java))
            finish()
        }else
            this.showToast("Email o contraseña incorrectos")



    }


    /**
     * verificar si todos los campos a registrar estan vacios o nulos
     * @return true si al menos uno de los campos esta vacio o es nulo -
     * false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emptyName=Validations.validateEditText(binding.idTxtLoginName)
        val emptyPass=Validations.validateEditText(binding.idTxtLoginPass)

        return emptyName || emptyPass
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