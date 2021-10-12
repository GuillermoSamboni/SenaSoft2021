package com.senasoft2021.senasoft2021.ui.login.user

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import com.senasoft2021.senasoft2021.MainActivity
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityRegisterBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.models.UserRegister
import com.senasoft2021.senasoft2021.validations.Validations
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var codeBar:Bitmap
    companion object{
        const val SAVE_CODE=2
        const val REQUEST_CODE_SCAN=0x22
        const val BITMAP=0x22
        lateinit var CODE_BAR: Bitmap
        const val HEIGHT=130
        const val WIDTH=130
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.idBtntRegisterMe.setOnClickListener { registerUser() }
        binding.idBtntRegisterGenerateQr.setOnClickListener { saveGenerateCode() }
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

    /**
     * Haemos la generacon del codigo qr de cada ususario
     */
    private fun saveGenerateCode(){
        var name=binding.idTxtRegisterName.text
        var phone=binding.idTxtRegisterPhone.text
        var email=binding.idTxtRegisterEmail.text

        val permisoWrite=ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!(name!!.isEmpty() || phone!!.isEmpty() || email!!.isEmpty() )){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), SAVE_CODE)
        }else{
            Toast.makeText(this, "Debe llenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCodeQrToGallery(context: Context,  bitmap: Bitmap, albumName:String){
        var file="${System.currentTimeMillis()}.jpg"
        var write:(OutputStream)->Boolean={ bitmap.compress(
            Bitmap.CompressFormat.PNG, 100, it)
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            val contentValues=ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, file)
                put(MediaStore.MediaColumns.MIME_TYPE, "images/*")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DCIM}/$albumName")
            }
            context.contentResolver.let {
                it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {uri->
                    it.openOutputStream(uri)?.let(write)
                }
            }
        }else{
            val imageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.pathSeparator + albumName
            val fileName=File(imageDir)
            if (fileName.exists()){
                fileName.mkdir()
            }
//            val image=File(imageDir, fileName)
//            write(FileOutputStream(image))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0]!=PackageManager.PERMISSION_GRANTED){ return }
        if (requestCode== BITMAP){
            var intentSave=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intentSave.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "images/*")
        }
        if (requestCode== SAVE_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                var datosCode=
                    binding.idTxtRegisterName.text.toString() +"\n" +
                            binding.idTxtRegisterEmail.text.toString() +"\n" +
                            binding.idTxtRegisterPhone.text.toString()
                val typeCode=HmsScan.QRCODE_SCAN_TYPE
                val options=HmsBuildBitmapOption.Creator().setBitmapMargin(3).setBitmapBackgroundColor(Color.WHITE).setBitmapColor(Color.BLACK).create()
                try {
                    CODE_BAR=ScanUtil.buildBitmap(datosCode,typeCode, WIDTH, HEIGHT, options)
                    codeBar= CODE_BAR
                    saveCodeQrToGallery(this, codeBar, "SenaSoft2021")
                    Toast.makeText(this, "Código generado", Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Log.d("ERROR GENERATE CODE",e.toString())
                }

            }
        }
    }

}