package com.example.tetsappforsmartapps.domain.repository

import androidx.lifecycle.LiveData
import com.example.tetsappforsmartapps.data.database.CountryDbModel
import com.example.tetsappforsmartapps.domain.entity.Country
import com.example.tetsappforsmartapps.domain.entity.GameSettings
import com.example.tetsappforsmartapps.domain.entity.Level

interface Repository {

    suspend fun loadData(countryName: String?): Country?

    fun getGameSettings(level: Level): GameSettings

    suspend fun addCountry(country: Country)

    fun getFirstLoadedCountry(): LiveData<List<Country>?>
}