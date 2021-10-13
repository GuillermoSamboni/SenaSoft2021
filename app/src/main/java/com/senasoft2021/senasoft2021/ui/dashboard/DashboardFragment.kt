package com.senasoft2021.senasoft2021.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.senasoft2021.senasoft2021.adapters.EventAdapter
import com.senasoft2021.senasoft2021.databinding.FragmentDashboardBinding
import com.senasoft2021.senasoft2021.models.EventRegister
import com.senasoft2021.senasoft2021.ui.dashboard.activitie.CompetenciaActivity
import com.senasoft2021.senasoft2021.ui.login.admin.EventsViewModel
import com.senasoft2021.senasoft2021.ui.login.admin.InfoEventFragment

class DashboardFragment : Fragment() {

    private lateinit var eventViewModel:EventsViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventViewModel =
            ViewModelProvider(requireActivity()).get(EventsViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.idBtnFloatDenuncias.setOnClickListener { startActivity(Intent(requireContext(), CompetenciaActivity::class.java)) }

        initRecyclerView()

        return root
    }

    private fun initRecyclerView() {
        val list= mutableListOf<EventRegister>()
        val adapterEvents=EventAdapter(list)
        eventViewModel.getEvents(requireContext()).observe(viewLifecycleOwner){

            list.apply {
                clear()
                addAll(it)
                adapterEvents.notifyDataSetChanged()
            }

            if(list.isEmpty()){
                binding.idLinearDashBoard.visibility=View.VISIBLE
                binding.idRcyDashBoardList.visibility=View.GONE
            }

            binding.idRcyDashBoardList.apply {
                adapter=adapterEvents
            }

            adapterEvents.setOnClickListener{
                val position=binding.idRcyDashBoardList.getChildAdapterPosition(it)
                val event=list[position]
                eventViewModel.setEvent(event)
                val infoEvent=InfoEventFragment()
                infoEvent.show(childFragmentManager,"")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}