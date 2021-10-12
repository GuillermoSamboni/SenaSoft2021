package com.senasoft2021.senasoft2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.databinding.ItemEventBinding
import com.senasoft2021.senasoft2021.models.EventRegister

class EventAdapter(private val list:MutableList<EventRegister>): RecyclerView.Adapter<EventAdapter.ViewHolder>(), View.OnClickListener {

    private var clickLister:View.OnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemEventBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    class ViewHolder(private val binding:ItemEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventRegister) {


            binding.idTxtItemEventTitle.text=event.title
            binding.idTxtItemEventDescrip.text=event.description
            Glide.with(binding.idImgItemEvent).load(event.image).into(binding.idImgItemEvent)

        }


    }

    fun setOnClickListener(listener:View.OnClickListener){
        clickLister=listener
    }

    override fun onClick(v: View?) {
        clickLister?.onClick(v)
    }

}