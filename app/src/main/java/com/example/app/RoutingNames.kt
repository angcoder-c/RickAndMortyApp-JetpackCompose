package com.example.app

import kotlinx.serialization.Serializable

sealed class RoutingNames() {
    @Serializable
    data object MainScreen : RoutingNames()

    @Serializable
    data object LoginScreen : RoutingNames()

    @Serializable
    data object ProfileScreen : RoutingNames()

    @Serializable
    data class CharacterDetailScreen(
        val characterId: Int
    ) : RoutingNames()

    @Serializable
    data object LocationsScreen : RoutingNames()

    @Serializable
    data class LocationsDetailScreen(
        val locationsId: Int
    ) : RoutingNames()

}
