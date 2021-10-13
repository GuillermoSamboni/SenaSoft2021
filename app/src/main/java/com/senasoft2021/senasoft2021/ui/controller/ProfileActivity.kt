package com.senasoft2021.senasoft2021.ui.controller

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.InterstitialAd
import com.huawei.hms.identity.Address
import com.huawei.hms.identity.entity.GetUserAddressResult
import com.huawei.hms.identity.entity.UserAddress
import com.huawei.hms.identity.entity.UserAddressRequest
import com.huawei.hms.support.api.client.Status
import com.huawei.openalliance.ad.beans.parameter.AdSlotParam
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.ActivityProfileBinding
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding

    private var intetrescial:InterstitialAd?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idBtnFloatDeniuncia.setOnClickListener {
            identityKit()

            Toast.makeText(this, "hello identity", Toast.LENGTH_SHORT).show()
        }

        intetrescial = InterstitialAd(this)
        intetrescial!!.adId = "testb4znbuh3n2"
        adsLoad()

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        showAds()
    }

    /**
     * craempo el fomulario de identidad del usuario
     */
    private fun identityKit() {
        var task=Address.getAddressClient(this@ProfileActivity).getUserAddress(UserAddressRequest())
        task.apply {
            addOnCompleteListener {
                try {
                    startActiviyForResult(it.result)
                }catch (e:Exception){
                    Log.d("ERROR", e.toString())
                }
            }
        }
    }

    private fun startActiviyForResult(it: GetUserAddressResult) {
        val status:Status=it.status
        if (it.returnCode==0 && status.hasResolution()){
            status.startResolutionForResult(this@ProfileActivity, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Activity.RESULT_OK->{
                var userAddres:UserAddress= UserAddress.parseIntent(data)
            }
        }

    }

    /***
     * cargamos el anuncio
     */
    private fun adsLoad(){
        var adsParam=AdParam.Builder().build()
        intetrescial!!.loadAd(adsParam)
    }
    private fun showAds(){
        if (intetrescial!=null){
            if (intetrescial!!.isLoaded){

            }else{
                finishAfterTransition()
            }
        }
    }

}