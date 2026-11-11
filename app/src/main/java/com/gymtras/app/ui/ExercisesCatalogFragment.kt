package com.gymtras.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymtras.app.R
import com.gymtras.app.adapters.CatalogExerciseAdapter
import com.gymtras.app.databinding.FragmentExercisesCatalogBinding
import com.gymtras.app.models.CatalogExercise

class ExercisesCatalogFragment : Fragment() {

    private var _binding: FragmentExercisesCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CatalogExerciseAdapter
    private var allExercises = mutableListOf<CatalogExercise>()
    private var selectedMuscleGroup: String = "Todos"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExercisesCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSampleCatalog()
        setupRecyclerView()
        setupFilters()
        setupSearch()
    }

    private fun loadSampleCatalog() {
        allExercises = mutableListOf(
            CatalogExercise(name = "Press de Banca Plano", muscleGroup = "Pecho", equipment = "Barra libre", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Press Inclinado con Mancuernas", muscleGroup = "Pecho", equipment = "Mancuernas", recommendedRest = "60 - 90 seg"),
            CatalogExercise(name = "Aperturas en Polea Alta", muscleGroup = "Pecho", equipment = "Máquina / Polea", recommendedRest = "60 seg"),
            CatalogExercise(name = "Remo con Barra T", muscleGroup = "Espalda", equipment = "Barra libre", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Jalón al Pecho en Polea", muscleGroup = "Espalda", equipment = "Máquina / Polea", recommendedRest = "60 - 90 seg"),
            CatalogExercise(name = "Dominadas Pronadas", muscleGroup = "Espalda", equipment = "Peso corporal", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Sentadilla Trasera con Barra", muscleGroup = "Piernas", equipment = "Barra libre", recommendedRest = "120 - 180 seg"),
            CatalogExercise(name = "Prensa de Pierna 45°", muscleGroup = "Piernas", equipment = "Máquina", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Peso Muerto Rumano", muscleGroup = "Piernas", equipment = "Barra libre", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Press Militar con Barra", muscleGroup = "Hombros", equipment = "Barra libre", recommendedRest = "90 - 120 seg"),
            CatalogExercise(name = "Elevaciones Laterales", muscleGroup = "Hombros", equipment = "Mancuernas", recommendedRest = "60 seg"),
            CatalogExercise(name = "Curl de Bíceps con Barra Z", muscleGroup = "Brazos", equipment = "Barra libre", recommendedRest = "60 - 90 seg"),
            CatalogExercise(name = "Extensiones de Tríceps en Polea", muscleGroup = "Brazos", equipment = "Máquina / Polea", recommendedRest = "60 seg"),
            CatalogExercise(name = "Elevación de Piernas Colgado", muscleGroup = "Core / Abdomen", equipment = "Peso corporal", recommendedRest = "60 seg")
        )
    }

    private fun setupRecyclerView() {
        adapter = CatalogExerciseAdapter(allExercises)
        binding.rvExercisesCatalog.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExercisesCatalog.adapter = adapter
    }

    private fun setupFilters() {
        binding.chipGroupMuscle.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener
            val chipId = checkedIds.first()
            selectedMuscleGroup = when (chipId) {
                R.id.chipChest -> "Pecho"
                R.id.chipBack -> "Espalda"
                R.id.chipLegs -> "Piernas"
                R.id.chipShoulders -> "Hombros"
                R.id.chipArms -> "Brazos"
                R.id.chipCore -> "Core / Abdomen"
                else -> "Todos"
            }
            applyFilters()
        }
    }

    private fun setupSearch() {
        binding.etSearchExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun applyFilters() {
        val query = binding.etSearchExercise.text.toString().trim()
        val filtered = allExercises.filter { exercise ->
            val matchesMuscle = selectedMuscleGroup == "Todos" || exercise.muscleGroup.contains(selectedMuscleGroup, ignoreCase = true)
            val matchesQuery = query.isBlank() || exercise.name.contains(query, ignoreCase = true) || exercise.equipment.contains(query, ignoreCase = true)
            matchesMuscle && matchesQuery
        }
        adapter.updateData(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
