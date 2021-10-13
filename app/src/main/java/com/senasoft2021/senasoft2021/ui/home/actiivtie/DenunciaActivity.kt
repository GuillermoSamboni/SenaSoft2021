package com.senasoft2021.senasoft2021.ui.home.actiivtie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.senasoft2021.senasoft2021.adapters.DenunciaAdapter
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityDenunciaBinding

class DenunciaActivity : AppCompatActivity() {
    lateinit var binding:ActivityDenunciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding= ActivityDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnFloatDenuncias.setOnClickListener {
            val createDenuncia=CreateDenunciaFragment()
            createDenuncia.show(supportFragmentManager,"")
        }

        initRecyclerView()


    }

    private fun initRecyclerView() {

        val list=RoomDatabaseClient.listAllDenuncias(this)
        val adapterDenuncias=DenunciaAdapter(list)

        if(list.isEmpty()){
            binding.idLinearDenuncias.visibility= View.VISIBLE
            binding.idRcyDenunciasList.visibility=View.GONE
        }

        binding.idRcyDenunciasList.apply {
            adapter=adapterDenuncias
        }

        adapterDenuncias.setOnClickListener{
            val position=binding.idRcyDenunciasList.getChildAdapterPosition(it)
            val denuncia=list[position]
            val infoDenuncia=InfoDenunciaFragment(denuncia)
            infoDenuncia.show(supportFragmentManager,"")
        }

    }

    override fun onRestart() {
        super.onRestart()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }


}