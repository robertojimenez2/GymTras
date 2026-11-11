package com.gymtras.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gymtras.app.R
import com.gymtras.app.adapters.RoutineExerciseAdapter
import com.gymtras.app.databinding.FragmentRoutineEditorBinding
import com.gymtras.app.models.RoutineExercise

class RoutineEditorFragment : Fragment() {

    private var _binding: FragmentRoutineEditorBinding? = null
    private val binding get() = _binding!!

    private val exercisesList = mutableListOf<RoutineExercise>()
    private lateinit var adapter: RoutineExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupRecyclerView()
        setupButtons()
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias_array,
            R.layout.spinner_item
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }
        binding.spRoutineCategory.adapter = spinnerAdapter
    }

    private fun setupRecyclerView() {
        adapter = RoutineExerciseAdapter(exercisesList) { exerciseToRemove ->
            adapter.removeExercise(exerciseToRemove)
            updateExerciseCount()
        }
        binding.rvRoutineExercises.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutineExercises.adapter = adapter
        updateExerciseCount()
    }

    private fun setupButtons() {
        binding.btnAddExerciseToRoutine.setOnClickListener {
            val name = binding.etExerciseName.text.toString().trim()
            val weightStr = binding.etExerciseWeight.text.toString().trim()
            val setsStr = binding.etExerciseSets.text.toString().trim()
            val repsStr = binding.etExerciseReps.text.toString().trim()
            val restStr = binding.etExerciseRest.text.toString().trim()

            if (name.isEmpty()) {
                binding.tilExerciseName.error = getString(R.string.error_fill_required)
                binding.etExerciseName.requestFocus()
                return@setOnClickListener
            } else {
                binding.tilExerciseName.error = null
            }

            val weight = weightStr.toDoubleOrNull() ?: 0.0
            val sets = setsStr.toIntOrNull() ?: 4
            val reps = repsStr.toIntOrNull() ?: 10
            val rest = restStr.toIntOrNull() ?: 90

            val newExercise = RoutineExercise(
                name = name,
                weightKg = weight,
                sets = sets,
                reps = reps,
                restSeconds = rest
            )

            adapter.addExercise(newExercise)
            updateExerciseCount()

            // Reset inputs
            binding.etExerciseName.setText("")
            binding.etExerciseWeight.setText("")
            binding.etExerciseSets.setText("")
            binding.etExerciseReps.setText("")
            binding.etExerciseRest.setText("")

            Toast.makeText(
                requireContext(),
                getString(R.string.toast_exercise_added, name),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnSaveRoutine.setOnClickListener {
            val routineName = binding.etRoutineName.text.toString().trim()

            if (routineName.isEmpty()) {
                binding.tilRoutineName.error = getString(R.string.error_routine_name_required)
                binding.etRoutineName.requestFocus()
                return@setOnClickListener
            } else {
                binding.tilRoutineName.error = null
            }

            if (exercisesList.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_routine_empty),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            Toast.makeText(
                requireContext(),
                getString(R.string.toast_routine_saved, routineName, exercisesList.size),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateExerciseCount() {
        val count = adapter.itemCount
        binding.tvExerciseListHeader.text = getString(R.string.label_exercise_list, count)
        if (count == 0) {
            binding.tvEmptyExercises.visibility = View.VISIBLE
        } else {
            binding.tvEmptyExercises.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
