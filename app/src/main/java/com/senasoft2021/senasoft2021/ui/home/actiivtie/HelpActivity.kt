package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import com.senasoft2021.senasoft2021.databinding.ActivityHelpBinding
import com.senasoft2021.senasoft2021.huawei.location.LocationService


class HelpActivity : AppCompatActivity() {
    lateinit var binding:ActivityHelpBinding
    var requesCodeCamera=1
    var requesCodeStorage=2
    private var uriImage:Uri?=null
    private var locationService: LocationService? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
<<<<<<< HEAD

=======
        supportActionBar?.hide()
        locationService?.starRequest()
>>>>>>> 83567c26419f5c4d53ad9357c495a626fa0e330d

        binding.idBtnCamera.setOnClickListener { openCamera() }
        binding.idBtnStorage.setOnClickListener { openStorage() }
        binding.idBtnSend.setOnClickListener {
            sendMessageHelp() }

    }

    private fun openStorage() {
        var permisosStorage=ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        var intentStorage=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (permisosStorage==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requesCodeStorage)
            }else{
                startActivityForResult(intentStorage, requesCodeStorage)
            }
        }
    }

    private fun openCamera() {
        var permisosCamera=ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        var intentCamera=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (permisosCamera==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), requesCodeCamera)
            }else{
                startActivityForResult(intentCamera, requesCodeCamera)
            }
        }
    }

    /**
     * vemos si se ha tomado algun foto
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==requesCodeCamera){

        }else if (requestCode==requesCodeStorage){
            if (resultCode==RESULT_OK && data!=null){
                uriImage=data.data
                binding.idimageView.setImageURI(uriImage)
            }
        }
    }

    /**
     * Envia,ops mensaje
     */

    private fun sendMessageHelp(){
<<<<<<< HEAD

        if (binding.idTxtMessageHelp.text.isNotEmpty() && uriImage!=null) {
            var intentSend = Intent()
            intentSend.action = Intent.ACTION_VIEW
            val defaultNumber = "+57 3163254647"
            var stingMessage = "whatsapp://send?phone=+ $defaultNumber" + "${binding.idTxtMessageHelp.text}"
            intentSend.data = Uri.parse(stingMessage)
=======
        if (binding.idTxtMessageHelp.text!!.isNotEmpty() && uriImage!=null){
            var intentSend=Intent()
            intentSend.action=Intent.ACTION_VIEW
            val defaultNumber="+57 3163254647"
            var stingMessage="whatsapp://send?phone=+ $defaultNumber" + "\n ${binding.idTxtMessageHelp.text}" + Constantes.myLocation
            intentSend.data= Uri.parse(stingMessage)
>>>>>>> 83567c26419f5c4d53ad9357c495a626fa0e330d
            startActivity(intentSend)
        }else if(binding.idTxtMessageHelp.text.isEmpty() && uriImage==null){
            Toast.makeText(this, "holaa", Toast.LENGTH_SHORT).show()
            var sendintent=Intent()
            sendintent.action=Intent.ACTION_SEND
            sendintent.type="image/*"
            sendintent.type="text/plain"
            sendintent.setPackage("com.whatsapp")
            if (uriImage!=null && binding.idTxtMessageHelp.text.toString().isNotEmpty()){
                sendintent.putExtra(Intent.EXTRA_STREAM, uriImage)
                sendintent.putExtra(Intent.EXTRA_TEXT, binding.idTxtMessageHelp.text.toString())
                try{
                    startActivity(sendintent)
                }catch (e:Exception){
                    Log.d("Error send", e.toString())
                }
            }
            else{
                Toast.makeText(this, "no se eligio ninguna imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMessgeLocation(){
        if (locationService==null){
            locationService= LocationService(this)
            locationService!!.starRequest()
        }
    }
}