package com.gymtras.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gymtras.app.R
import com.gymtras.app.databinding.FragmentHomeDashboardBinding

class HomeDashboardFragment : Fragment() {

    private var _binding: FragmentHomeDashboardBinding? = null
    private val binding get() = _binding!!

    var onNavigateToPage: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActions()
    }

    private fun setupActions() {
        binding.btnStartWorkout.setOnClickListener {
            onNavigateToPage?.invoke(R.id.nav_routines)
        }

        binding.btnCreateFirstRoutineCTA.setOnClickListener {
            onNavigateToPage?.invoke(R.id.nav_routines)
        }

        binding.btnQuickLogWorkout.setOnClickListener {
            onNavigateToPage?.invoke(R.id.nav_routines)
        }

        binding.cardMuscleChest.setOnClickListener { onNavigateToPage?.invoke(R.id.nav_exercises) }
        binding.cardMuscleBack.setOnClickListener { onNavigateToPage?.invoke(R.id.nav_exercises) }
        binding.cardMuscleLegs.setOnClickListener { onNavigateToPage?.invoke(R.id.nav_exercises) }
        binding.cardMuscleShoulders.setOnClickListener { onNavigateToPage?.invoke(R.id.nav_exercises) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
