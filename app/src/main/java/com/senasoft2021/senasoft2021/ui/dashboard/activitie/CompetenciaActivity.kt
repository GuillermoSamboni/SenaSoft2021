package com.senasoft2021.senasoft2021.ui.dashboard.activitie

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.huawei.hms.videoeditor.ui.api.MediaApplication
import com.huawei.hms.videoeditor.ui.api.MediaApplication.START_MODE_IMPORT_FROM_MEDIA
import com.huawei.hms.videoeditor.ui.api.MediaExportCallBack
import com.huawei.hms.videoeditor.ui.api.MediaInfo
import com.huawei.hms.videoeditor.ui.api.VideoEditorLaunchOption
import com.senasoft2021.senasoft2021.databinding.ActivityCompetenciaBinding


class CompetenciaActivity : AppCompatActivity() {
    lateinit var binding:ActivityCompetenciaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        binding= ActivityCompetenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permisosStorage()

        binding.idBtnEditarVideo.setOnClickListener { editarVideoCompetncia() }


    }

    private fun editarVideoCompetncia() {
        MediaApplication.getInstance().setApiKey("CwEAAAAAhyXk7a+Aj8V7NwMWAdYEvgKyxmy29DdF9Sd1yYfiR1KONfrpFBgrt9gdHmdJA2+ESTGuQyoV/5NFqPYutlki6dasoE8=")
        MediaApplication.getInstance().setLicenseId("354DB3460FB49FDDDDDAE5F2D4F7EFA9A8FF2A1C7962E3B7EF107CE031C174CC")

        MediaApplication.getInstance().setOnMediaExportCallBack(callBack)
        var optionsEditor:VideoEditorLaunchOption=VideoEditorLaunchOption.Builder().setStartMode(START_MODE_IMPORT_FROM_MEDIA).build()
        MediaApplication.getInstance().launchEditorActivity(this, optionsEditor)
    }

    private val callBack: MediaExportCallBack = object : MediaExportCallBack {
        override fun onMediaExportSuccess(mediaInfo: MediaInfo) {
            val mediaPath = mediaInfo.mediaPath
        }
        override fun onMediaExportFailed(errorCode: Int) {

        }
    }

    private fun permisosStorage(){
        var permisos=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (permisos==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 10)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 10)
            }
        }else{
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }
    }

}