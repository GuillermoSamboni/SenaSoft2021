package com.senasoft2021.senasoft2021.ui.home.actiivtie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.senasoft2021.senasoft2021.adapters.DenunciaAdapter
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.ActivityDenunciaBinding
import com.senasoft2021.senasoft2021.models.DenunciaRegister

class DenunciaActivity : AppCompatActivity() {
    lateinit var binding:ActivityDenunciaBinding
    private lateinit var denunciaViewModel: DenunciaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding= ActivityDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        denunciaViewModel=ViewModelProvider(this).get(DenunciaViewModel::class.java)
        binding.idBtnFloatDenuncias.setOnClickListener {
            val createDenuncia=CreateDenunciaFragment()
            createDenuncia.show(supportFragmentManager,"")
        }

        initRecyclerView()


    }

    /**
     * inicializar el recyclerView
     * y agregarle el evento onclick a el adapter
     */
    private fun initRecyclerView() {

        val list= mutableListOf<DenunciaRegister>()
        val adapterDenuncias=DenunciaAdapter(list)

        denunciaViewModel.getDenuncias(this).observe(this){
            list.clear()
            list.addAll(it)
            adapterDenuncias.notifyDataSetChanged()

            if(list.isEmpty()){
                binding.idLinearDenuncias.visibility= View.VISIBLE
                binding.idRcyDenunciasList.visibility=View.GONE
            }

        }





        binding.idRcyDenunciasList.apply {
            adapter=adapterDenuncias
        }

        adapterDenuncias.setOnClickListener{
            val position=binding.idRcyDenunciasList.getChildAdapterPosition(it)
            val denuncia=list[position]
            //mostrar la info de la denuncia seleccionada
            denunciaViewModel.setDenuncia(denuncia)
            val infoDenuncia=InfoDenunciaFragment()
            infoDenuncia.show(supportFragmentManager,"")
        }

    }



}