package com.senasoft2021.senasoft2021.ui.login.admin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    lateinit var binding:ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminBinding.inflate(layoutInflater)
        this.supportActionBar?.hide()
        setContentView(binding.root)
        binding.ibBtnScanner.setOnClickListener { startActivity(Intent(this, ScaneoActivity::class.java)) }
    }
}