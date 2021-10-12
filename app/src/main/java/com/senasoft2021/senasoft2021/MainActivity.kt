package com.senasoft2021.senasoft2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityMainBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.ui.login.user.RegisterActivity
import com.senasoft2021.senasoft2021.validations.Validations

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnLoginRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.idBtnLoginGoLoginAdmin.setOnClickListener {
            //startActivity(Intent(this,LoginAdminActivity::class.java))
        }

        binding.idBtnLogin.setOnClickListener { loginUser() }

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
}