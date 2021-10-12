package com.senasoft2021.senasoft2021.ui.login.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.FragmentCreateEventBinding
import com.senasoft2021.senasoft2021.databinding.FragmentHomeAdminBinding


class CreateEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentCreateEventBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCreateEventBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}