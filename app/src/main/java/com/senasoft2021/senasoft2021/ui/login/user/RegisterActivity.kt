package com.senasoft2021.senasoft2021.ui.login.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.senasoft2021.senasoft2021.MainActivity
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityRegisterBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.models.UserRegister
import com.senasoft2021.senasoft2021.validations.Validations

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtntRegisterMe.setOnClickListener { registerUser() }

    }


    /**
     * registrar un nuevo usuario
     */
    private fun registerUser() {


        //validar si los campos estan vacios
        if(validarCampos()) {
            this.showToast("Los campos no pueden estar vacios")
            return
        }

        val name=binding.idTxtRegisterName.text.toString().trim()
        val phone=binding.idTxtRegisterPhone.text.toString().trim()
        val email=binding.idTxtRegisterEmail.text.toString().trim()
        val pass=binding.idTxtRegisterPass.text.toString().trim()
        val confirmpass=binding.idTxtRegisterConfirmPass.text.toString().trim()


        //verificar si el email ingresado es real
        if(!emailReal(email)){
            this.showToast("Ingrese un email real")
            return
        }

        //verificar si el nombre ya esta registrado
        if(RoomDatabaseClient.validarNombreUsuario(name,this)){
            this.showToast("El nombre ya esta registrado")
            return
        }

        //verificar si el email del usuario ya esta registrado
        if(RoomDatabaseClient.validarEmailUsuario(email,this)){
            this.showToast("El email ya esta registrado")
            return
        }

        //verificar si las contraseña coinciden
        if(pass != confirmpass){
            this.showToast("Las contraseñas no coinciden")
            return
        }

        val user=UserRegister(0,name,phone,email,pass)

        //registrar al usuario
        if(RoomDatabaseClient.registerUser(this,user)){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else
            this.showToast("Ha ocurrrido un error")




    }


    /**
     * validar todos los campos de registro
     * @return true si al menos uno de los campos esta vacio o es nulo -
     * false en caso contrario
     */
    fun validarCampos(): Boolean {

        val emptyName=Validations.validateEditText(binding.idTxtRegisterName)
        val emptyPhone=Validations.validateEditText(binding.idTxtRegisterPhone)
        val emptyEmail=Validations.validateEditText(binding.idTxtRegisterEmail)
        val emptyPass=Validations.validateEditText(binding.idTxtRegisterPass)
        val emptyConfirmPass=Validations.validateEditText(binding.idTxtRegisterConfirmPass)

        return emptyName || emptyPhone || emptyEmail || emptyPass || emptyConfirmPass

    }

    /**
     * verificar si el email es real
     * @return true si el email es real - false si no lo es
     */
    fun emailReal(email:String): Boolean {
        val patter= Patterns.EMAIL_ADDRESS
        return patter.matcher(email).matches()
    }


}