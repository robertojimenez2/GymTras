package com.gymtras.app.models

import java.util.UUID

data class WorkoutLogEntry(
    val id: String = UUID.randomUUID().toString(),
    val exerciseName: String,
    val date: String,
    val category: String,
    val equipment: String,
    val weightKg: Double,
    val sets: Int,
    val reps: Int,
    val restSeconds: Int,
    val isEpic: Boolean,
    val notes: String
)
