package com.gymtras.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymtras.app.databinding.ItemRoutineExerciseBinding
import com.gymtras.app.models.RoutineExercise

class RoutineExerciseAdapter(
    private val exercises: MutableList<RoutineExercise>,
    private val onDeleteClick: (RoutineExercise) -> Unit
) : RecyclerView.Adapter<RoutineExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(val binding: ItemRoutineExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemRoutineExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = exercises[position]
        with(holder.binding) {
            tvExerciseNumber.text = (position + 1).toString()
            tvExerciseName.text = item.name
            tvWeightChip.text = "🏋️ ${item.weightKg} kg"
            tvSetsRepsChip.text = "${item.sets} series x ${item.reps} reps"
            tvRestChip.text = "⏱ ${item.restSeconds}s"

            btnDeleteExercise.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun getItemCount(): Int = exercises.size

    fun addExercise(exercise: RoutineExercise) {
        exercises.add(exercise)
        notifyItemInserted(exercises.size - 1)
    }

    fun removeExercise(exercise: RoutineExercise) {
        val index = exercises.indexOf(exercise)
        if (index != -1) {
            exercises.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, exercises.size)
        }
    }

    fun getExercises(): List<RoutineExercise> = exercises
}
