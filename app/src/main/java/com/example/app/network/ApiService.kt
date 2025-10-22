package com.example.app.network

import com.example.app.network.dto.CharacterResponseDto
import com.example.app.network.dto.LocationResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val client: HttpClient
) {
    private val baseUrl = "https://rickandmortyapi.com/api"

    suspend fun getCharacters(page: Int = 1): CharacterResponseDto {
        return client.get("$baseUrl/character?page=$page").body()
    }

    suspend fun getCharacterById(id: Int): CharacterResponseDto {
        return client.get("$baseUrl/character/$id").body()
    }

    suspend fun getLocations(page: Int = 1): LocationResponseDto {
        return client.get("$baseUrl/location?page=$page").body()
    }

    suspend fun getLocationById(id: Int): LocationResponseDto {
        return client.get("$baseUrl/location/$id").body()
    }
}