package com.quadrado.movies_app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "pesquisa de filme"
    }
    val text: LiveData<String> = _text
}