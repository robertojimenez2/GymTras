package com.gymtras.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymtras.app.adapters.WorkoutLogAdapter
import com.gymtras.app.databinding.FragmentWorkoutLogBinding
import com.gymtras.app.models.WorkoutLogEntry

class WorkoutLogFragment : Fragment() {

    private var _binding: FragmentWorkoutLogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WorkoutLogAdapter
    private var allLogs = mutableListOf<WorkoutLogEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSampleData()
        setupRecyclerView()
        setupStats()
        setupSearch()
    }

    private fun loadSampleData() {
        allLogs = mutableListOf(
            WorkoutLogEntry(
                exerciseName = "Sentadilla Trasera con Barra",
                date = "15/10/2026",
                category = "Hipertrofia",
                equipment = "Barra libre",
                weightKg = 100.0,
                sets = 4,
                reps = 10,
                restSeconds = 120,
                isEpic = true,
                notes = "Sensaciones increíbles. Se rompió marca personal en la última serie."
            ),
            WorkoutLogEntry(
                exerciseName = "Press de Banca Plano",
                date = "14/10/2026",
                category = "Fuerza",
                equipment = "Barra libre",
                weightKg = 85.0,
                sets = 5,
                reps = 5,
                restSeconds = 180,
                isEpic = true,
                notes = "Pausa de 1 segundo en el pecho."
            ),
            WorkoutLogEntry(
                exerciseName = "Dominadas Lastradas",
                date = "12/10/2026",
                category = "Fuerza",
                equipment = "Peso corporal",
                weightKg = 15.0,
                sets = 4,
                reps = 8,
                restSeconds = 90,
                isEpic = false,
                notes = "Control estricto sin balanceo."
            ),
            WorkoutLogEntry(
                exerciseName = "Press Militar con Mancuernas",
                date = "10/10/2026",
                category = "Hipertrofia",
                equipment = "Mancuernas",
                weightKg = 24.0,
                sets = 4,
                reps = 12,
                restSeconds = 60,
                isEpic = false,
                notes = "Buen bombeo en hombros."
            )
        )
    }

    private fun setupRecyclerView() {
        adapter = WorkoutLogAdapter(allLogs)
        binding.rvWorkoutLog.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkoutLog.adapter = adapter
    }

    private fun setupStats() {
        val totalWorkouts = allLogs.size
        val epicCount = allLogs.count { it.isEpic }
        val maxWeight = allLogs.maxOfOrNull { it.weightKg } ?: 0.0

        binding.tvStatTotalWorkouts.text = totalWorkouts.toString()
        binding.tvStatEpicCount.text = epicCount.toString()
        binding.tvStatMaxWeight.text = "${maxWeight.toInt()}kg"
    }

    private fun setupSearch() {
        binding.etSearchLog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterLogs(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterLogs(query: String) {
        val filtered = if (query.isBlank()) {
            allLogs
        } else {
            allLogs.filter { log ->
                log.exerciseName.contains(query, ignoreCase = true) ||
                        log.category.contains(query, ignoreCase = true) ||
                        log.notes.contains(query, ignoreCase = true)
            }
        }

        adapter.updateData(filtered)

        if (filtered.isEmpty()) {
            binding.rvWorkoutLog.visibility = View.GONE
            binding.emptyLogStateContainer.visibility = View.VISIBLE
        } else {
            binding.rvWorkoutLog.visibility = View.VISIBLE
            binding.emptyLogStateContainer.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
