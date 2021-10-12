package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.senasoft2021.senasoft2021.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    lateinit var binding:ActivityHelpBinding
    var requesCodeCamera=1
    var requesCodeStorage=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnCamera.setOnClickListener { openCamera() }
        binding.idBtnStorage.setOnClickListener { openStorage() }

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
}