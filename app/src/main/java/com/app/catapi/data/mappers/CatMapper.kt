package com.app.catapi.data.mappers

import com.app.catapi.model.CatItem
import com.app.catapi.data.remote.dto.CatDto

object CatMapper {

    fun mapToDomainModel(dto: CatDto): CatItem {
        with(dto) {
            return CatItem(
                id = id,
                src = src
            )
        }
    }
}