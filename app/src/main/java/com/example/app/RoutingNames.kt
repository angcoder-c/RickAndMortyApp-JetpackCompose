package com.example.app

import kotlinx.serialization.Serializable

sealed class RoutingNames() {
    @Serializable
    data object MainScreen : RoutingNames()

    @Serializable
    data object LoginScreen : RoutingNames()

    @Serializable
    data class CharacterDetailScreen(
        val characterId: Int
    ) : RoutingNames()
}
