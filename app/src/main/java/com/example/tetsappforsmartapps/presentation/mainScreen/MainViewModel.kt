package com.example.tetsappforsmartapps.presentation.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetsappforsmartapps.data.repositoryImpl.RepositoryImpl
import com.example.tetsappforsmartapps.domain.entity.Country
import kotlinx.coroutines.*

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val repositoryImpl = RepositoryImpl(application)

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    var isEmpty = true

    private val firstLoadedCountry: LiveData<List<Country>?> = repositoryImpl.getFirstLoadedCountry()

    fun makeRequest(name: String) {
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                repositoryImpl.loadData(name)
            }
            if (result != null) {
                repositoryImpl.addCountry(result)
            }
            _result.postValue(result?.name?.common.toString())
        }
    }

    fun getFirstLoadedCountry(): LiveData<List<Country>?> {
        isEmpty = firstLoadedCountry.value == null
        return firstLoadedCountry
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}