package com.dharmendra.sheekho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharmendra.domain.jikandata.JikanData
import com.dharmendra.domain.jikandatadetails.GetJikanDataDetailsUseCase
import com.dharmendra.domain.jikandatadetails.JikanDatadetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Optional
import javax.inject.Inject

@HiltViewModel
class PlayVideoDetailsViewModel @Inject constructor(private val useCase: GetJikanDataDetailsUseCase):ViewModel() {
    var _data = MutableStateFlow<JikanDatadetails>(JikanDatadetails())
    val data: StateFlow<JikanDatadetails> get() = _data

    fun getJikanDetails(animID:Int){
        viewModelScope.launch {
            try {
                val animList = useCase.getAnimeById(animeId = animID)
                _data.value = animList // update livedata
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}