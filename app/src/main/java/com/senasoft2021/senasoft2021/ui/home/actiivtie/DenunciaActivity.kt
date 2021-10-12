package com.senasoft2021.senasoft2021.ui.home.actiivtie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.ActivityDenunciaBinding

class DenunciaActivity : AppCompatActivity() {
    lateinit var binding:ActivityDenunciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding= ActivityDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}