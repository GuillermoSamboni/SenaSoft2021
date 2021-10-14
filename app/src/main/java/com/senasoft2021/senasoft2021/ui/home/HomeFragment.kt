package com.senasoft2021.senasoft2021.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.database.SharedPreferencesClient
import com.senasoft2021.senasoft2021.databinding.FragmentHomeBinding
import com.senasoft2021.senasoft2021.ui.home.actiivtie.DenunciaActivity
import com.senasoft2021.senasoft2021.ui.home.actiivtie.HelpActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.idBtnHelp.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    HelpActivity::class.java
                )
            )
        }
        binding.idBtnFloatDenuncias.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    DenunciaActivity::class.java
                )
            )
        }


        showTargets()

        return root
    }


    /**
     * enfocar los botones e indicar cual es su funcion,
     * solo si se accede a este apatado por primera vez
     */
    private fun showTargets() {

        val firstTime = SharedPreferencesClient.firstTimeInHome(requireContext())

        if (!firstTime) {
            val listTargest = mutableListOf<TapTarget>(
                TapTarget.forView(
                    binding.cardView,
                    getString(R.string.help),
                    getString(R.string.info_help)
                )
                    .tintTarget(false)
                    .dimColor(R.color.primary_color)
                    .textColor(R.color.white)
                    .targetCircleColor(R.color.secondary_color)
                    .cancelable(false),

                TapTarget.forView(
                    binding.idBtnFloatDenuncias,
                    getString(R.string.denuncias),
                    getString(R.string.info_denuncias)
                )
                    .dimColor(R.color.primary_color)
                    .textColor(R.color.white)
                    .tintTarget(false)
                    .targetCircleColor(R.color.secondary_color)
            )
            TapTargetSequence(requireActivity()).targets(listTargest).start()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}