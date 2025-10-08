package com.example.app.mappers

import com.example.app.Character
import com.example.app.Location
import com.example.app.database.entities.CharacterEntity
import com.example.app.database.entities.LocationEntity

// Character to CharacterEntity
fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

// CharacterEntity to Character
fun CharacterEntity.toModel(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

// Location to LocationEntity
fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}

// LocationEntity to Location
fun LocationEntity.toModel(): Location {
    return Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}