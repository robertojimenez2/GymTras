package com.gymtras.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymtras.app.R
import com.gymtras.app.databinding.ItemWorkoutLogEntryBinding
import com.gymtras.app.models.WorkoutLogEntry

class WorkoutLogAdapter(
    private var logs: List<WorkoutLogEntry>
) : RecyclerView.Adapter<WorkoutLogAdapter.LogViewHolder>() {

    inner class LogViewHolder(val binding: ItemWorkoutLogEntryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding = ItemWorkoutLogEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val item = logs[position]
        with(holder.binding) {
            tvLogExerciseName.text = item.exerciseName
            tvLogDate.text = "📅 ${item.date}"
            tvLogCategory.text = item.category
            tvLogEquipment.text = item.equipment

            tvLogWeightChip.text = "🏋️ ${item.weightKg} kg"
            tvLogSetsRepsChip.text = "${item.sets} series x ${item.reps} reps"
            tvLogRestChip.text = "⏱ ${item.restSeconds}s"

            if (item.isEpic) {
                tvLogEpicBadge.text = root.context.getString(R.string.tag_epic)
                tvLogEpicBadge.setBackgroundResource(R.drawable.bg_toggle_epic_on)
            } else {
                tvLogEpicBadge.text = root.context.getString(R.string.tag_normal)
                tvLogEpicBadge.setBackgroundResource(R.drawable.bg_toggle_epic_off)
            }

            if (item.notes.isNotBlank()) {
                tvLogNotes.visibility = View.VISIBLE
                tvLogNotes.text = "📝 ${item.notes}"
            } else {
                tvLogNotes.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = logs.size

    fun updateData(newLogs: List<WorkoutLogEntry>) {
        logs = newLogs
        notifyDataSetChanged()
    }
}
