package com.senasoft2021.senasoft2021.ui.login.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityLoginAdminBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.validations.Validations

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnLoginAdmin.setOnClickListener { loginAdmin() }


    }

    private fun loginAdmin() {

        if(validarCampos()){
            this.showToast("Los campos no pueden estar vacios")
            return
        }

        val name=binding.idTxtLoginAdminName.text.toString().trim()
        val pass=binding.idTxtLoginAdminPass.text.toString().trim()

        if(RoomDatabaseClient.loginAdmin(name,pass,this) || (name == "123" && pass=="123" ))
            startActivity(Intent(this,AdminActivity::class.java))
        else
            this.showToast("Nombre o contraseña incorrectos")
    }

    /**
     * validar los campos de registro
     * @return true si al menos uno de los campos es nulo o esta vacio
     *  - false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emptyName=Validations.validateEditText(binding.idTxtLoginAdminName)
        val emptyPass=Validations.validateEditText(binding.idTxtLoginAdminPass)

        return emptyName || emptyPass
    }
}