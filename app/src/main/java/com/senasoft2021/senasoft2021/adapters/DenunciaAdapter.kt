package com.senasoft2021.senasoft2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.databinding.ItemDenunciaBinding
import com.senasoft2021.senasoft2021.models.DenunciaRegister

class DenunciaAdapter(private val list:MutableList<DenunciaRegister>):
    RecyclerView.Adapter<DenunciaAdapter.ViewHolder>(), View.OnClickListener {

    private var clickLictener:View.OnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DenunciaAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemDenunciaBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DenunciaAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size



    class ViewHolder(private val binding:ItemDenunciaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(denuncia: DenunciaRegister) {

            binding.idTxtItemDenunciaTitle.text=denuncia.title
            binding.idTxtItemDenunciaDescrip.text=denuncia.description
            binding.idTxtItemDenunciaDate.text="Registrada el: ${denuncia.date}"
            Glide.with(binding.idImgItemDenunciaImage).load(denuncia.img).into(binding.idImgItemDenunciaImage)

        }

    }

    fun setOnClickListener(listener: View.OnClickListener){
        clickLictener=listener
    }

    override fun onClick(v: View?) {
        clickLictener?.onClick(v)
    }

}