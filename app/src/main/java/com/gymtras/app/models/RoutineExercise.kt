package com.gymtras.app.models

import java.util.UUID

data class RoutineExercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val weightKg: Double,
    val sets: Int,
    val reps: Int,
    val restSeconds: Int
)
