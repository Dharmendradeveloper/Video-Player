package com.dharmendra.sheekho

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharmendra.domain.jikandata.GetJikanListOfDataUseCase
import com.dharmendra.domain.jikandata.JikanData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val getJikanListOfDataUseCase: GetJikanListOfDataUseCase) :
    ViewModel() {
    var _data = MutableStateFlow<List<JikanData>>(emptyList())
    val data: StateFlow<List<JikanData>> get() = _data

      fun getJikanData() {
        viewModelScope.launch{
            try {
                val animList = getJikanListOfDataUseCase.getAnimeList()
                _data.value = animList // update livedata
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}