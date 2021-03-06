package com.senasoft2021.senasoft2021.ui.login.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.adapters.EventAdapter
import com.senasoft2021.senasoft2021.databinding.FragmentHomeAdminBinding
import com.senasoft2021.senasoft2021.models.EventRegister


class HomeAdminFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentHomeAdminBinding?=null
    private val binding get() = _binding!!
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsViewModel=ViewModelProvider(requireActivity()).get(EventsViewModel::class.java)

        binding.idBtnHomeAdminCreateEvent.setOnClickListener { findNavController().navigate(R.id.action_homeAdminFragment_to_createEventFragment)}

        initRecycler()

    }

    private fun initRecycler() {

        val eventList = mutableListOf<EventRegister>()
        val adapterEvents= EventAdapter(eventList)

        eventsViewModel.getEvents(requireContext()).observe(requireActivity()){

            if(it.isEmpty()){
                binding.idLinearHomeAdmin.visibility=View.VISIBLE
                binding.idRcyHomeAdminList.visibility=View.GONE
            }

            eventList.apply {
                clear()
                addAll(it)
                adapterEvents.notifyDataSetChanged()
            }

        }

        binding.idRcyHomeAdminList.apply {
            adapter=adapterEvents
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}