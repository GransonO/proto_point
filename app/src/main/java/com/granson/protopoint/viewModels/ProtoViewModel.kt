package com.granson.protopoint.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.granson.protopoint.interfaces.ResponseListener
import com.granson.protopoint.models.ProtoData
import com.granson.protopoint.repository.ProtoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProtoViewModel @ViewModelInject constructor(
    private val protoRepository: ProtoRepository,
) : ViewModel(), ResponseListener {

    var resetProtoDataList: MutableState<List<ProtoData>> = mutableStateOf(listOf())
    var protoDataList: MutableState<List<ProtoData>> = mutableStateOf(listOf())
    var isLoading: MutableState<Boolean> = mutableStateOf(false)
    var hasError: MutableState<String> = mutableStateOf("")

    var menuExpanded: MutableState<Boolean> = mutableStateOf(false)
    val suggestions: MutableState<List<String>> = mutableStateOf(listOf("Reset","Pending","Canceled","Delivered"))

    init {
        getProtoData()
    }

    private fun getProtoData(){
        viewModelScope.launch {
            // start loader here
            isLoading.value = true
            var result = protoRepository.getTheOrders()
            if(result.isNotEmpty()){
                protoDataList.value = result
                resetProtoDataList.value = result
            }else{
                hasError.value
            }
            isLoading.value = false
        }
    }

    fun toggleMenu(){
        menuExpanded.value = !menuExpanded.value
    }

    fun filterData(option: String){
        if(option.equals("Reset", ignoreCase = true)){
            protoDataList.value = resetProtoDataList.value
            menuExpanded.value = false
        }else{
            protoDataList.value = resetProtoDataList.value
            protoDataList.value = protoDataList.value.filter{ it.status.equals(option, ignoreCase = true) }
            menuExpanded.value = false
        }
    }

    override fun failed(message: String) {
        // Request error
        hasError.value = message
    }

}