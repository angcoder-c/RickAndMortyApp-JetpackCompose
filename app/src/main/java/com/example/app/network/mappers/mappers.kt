package com.example.app.network.mappers

import com.example.app.database.entities.CharacterEntity
import com.example.app.database.entities.LocationEntity
import com.example.app.network.dto.CharacterDto
import com.example.app.network.dto.LocationDto

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}

fun List<CharacterDto>.toCharacterEntities(): List<CharacterEntity> {
    return this.map { it.toEntity() }
}

fun List<LocationDto>.toLocationEntities(): List<LocationEntity> {
    return this.map { it.toEntity() }
}