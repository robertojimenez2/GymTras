package com.gymtras.app.models

import java.util.UUID

data class CatalogExercise(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val muscleGroup: String,
    val equipment: String,
    val recommendedRest: String
)
