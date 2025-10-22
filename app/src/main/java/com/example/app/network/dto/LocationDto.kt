package com.example.app.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    val info: InfoDto,
    val results: List<LocationDto>
)

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)