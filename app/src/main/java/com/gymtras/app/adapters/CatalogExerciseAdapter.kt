package com.gymtras.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymtras.app.databinding.ItemExerciseCardBinding
import com.gymtras.app.models.CatalogExercise

class CatalogExerciseAdapter(
    private var exercises: List<CatalogExercise>
) : RecyclerView.Adapter<CatalogExerciseAdapter.CatalogViewHolder>() {

    class CatalogViewHolder(val binding: ItemExerciseCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = ItemExerciseCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = exercises[position]
        with(holder.binding) {
            tvExerciseName.text = item.name
            tvExerciseMuscleAndEquipment.text = "Músculo: ${item.muscleGroup} • Equipo: ${item.equipment}"
            tvExerciseRecommendedRest.text = "⏱ Descanso sugerido: ${item.recommendedRest}"
        }
    }

    override fun getItemCount(): Int = exercises.size

    fun updateData(newExercises: List<CatalogExercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}
