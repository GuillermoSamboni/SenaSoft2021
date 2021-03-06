package com.senasoft2021.senasoft2021.ui.login.admin

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.huawei.hms.support.api.entity.common.CommonConstant
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.ActivityScaneoBinding
import com.senasoft2021.senasoft2021.ui.login.admin.ScaneoActivity.Companion.REQUEST_CODE_SCAN
import com.senasoft2021.senasoft2021.ui.login.user.RegisterActivity.Companion.REQUEST_CODE_SCAN

class ScaneoActivity : AppCompatActivity() {
    lateinit var binding:ActivityScaneoBinding
    companion object{
        const val DEFAULT_VIEW=0x21
        const val REQUEST_CODE_SCAN=0x01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding= ActivityScaneoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.idBtnScanear.setOnClickListener {scanearCodigoEvento()}

    }

    private fun scanearCodigoEvento() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), DEFAULT_VIEW)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val options=HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()

        if (requestCode== REQUEST_CODE_SCAN && grantResults.size<2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
            return
        }
        if (requestCode==DEFAULT_VIEW){
            ScanUtil.startScan(this as Activity?, REQUEST_CODE_SCAN, options)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_OK || data==null){
            return
        }
        if (requestCode==REQUEST_CODE_SCAN){
            var obj=data.getParcelableExtra(ScanUtil.RESULT) as HmsScan?
            obj?.showResult
            val alerDialog= androidx.appcompat.app.AlertDialog.Builder(this)
            alerDialog.setCancelable(false)
                .setTitle("RESULTADO DEL SCANEO")
                .setMessage("Resultado  ${obj?.showResult}")
                .setPositiveButton("Siguiente"){_,_->
                    alerDialog.setCancelable(false)

                }
            alerDialog.create().show()

            Toast.makeText(this, "${obj?.showResult}", Toast.LENGTH_SHORT).show()
        }
    }
}