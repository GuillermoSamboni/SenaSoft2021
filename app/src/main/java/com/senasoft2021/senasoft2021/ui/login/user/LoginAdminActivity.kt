package com.senasoft2021.senasoft2021.ui.login.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.ActivityLoginAdminBinding

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}