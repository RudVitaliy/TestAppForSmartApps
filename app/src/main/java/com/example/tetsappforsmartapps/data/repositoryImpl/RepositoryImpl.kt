package com.example.tetsappforsmartapps.data.repositoryImpl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.tetsappforsmartapps.data.database.AppDatabase
import com.example.tetsappforsmartapps.data.database.CountryDbModel
import com.example.tetsappforsmartapps.data.database.Dao
import com.example.tetsappforsmartapps.data.mapper.CountryMapper
import com.example.tetsappforsmartapps.data.networking.RestCountriesApi
import com.example.tetsappforsmartapps.data.networking.restCountriesApi
import com.example.tetsappforsmartapps.domain.entity.Country
import com.example.tetsappforsmartapps.domain.entity.GameSettings
import com.example.tetsappforsmartapps.domain.entity.Level
import com.example.tetsappforsmartapps.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl(
    private val application: Application
): Repository {

    private val mapper = CountryMapper()
    private val dao = AppDatabase.getInstance(application).getDao()

    override suspend fun loadData(countryName: String?): Country? {
        var countries: List<Country>? = null
        try {
            countries = countryName?.let { restCountriesApi.getCountryByName(it) }
        } catch (_: Exception) {
        }
        return countries?.get(0)
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    10,
                    2,
                )
            }
            Level.EASY -> {
                GameSettings(
                    20,
                    10,
                    3,
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    30,
                    15,
                    4,
                )
            }
            Level.HARD -> {
                GameSettings(
                    40,
                    20,
                    5,
                )
            }
        }
    }

    override suspend fun addCountry(country: Country) {
        dao.addCountryName(mapper.mapEntityToDbModel(country))
    }

    override fun getFirstLoadedCountry(): LiveData<List<Country>?> = Transformations.map(
        dao.getFirstCountry()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }

}