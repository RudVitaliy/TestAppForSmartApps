package com.example.tetsappforsmartapps.data.mapper

import com.example.tetsappforsmartapps.data.database.CountryDbModel
import com.example.tetsappforsmartapps.domain.entity.Country

class CountryMapper {
    fun mapDbModelToEntity(dbModel: CountryDbModel) = Country(
        id = dbModel.id,
        name = dbModel.name,
    )

    fun mapEntityToDbModel(country: Country) = CountryDbModel(
        id = country.id,
        name = country.name
    )

    fun mapListDbModelToListEntity(list: List<CountryDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}