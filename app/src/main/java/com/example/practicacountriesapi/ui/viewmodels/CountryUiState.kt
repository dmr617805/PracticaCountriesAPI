package com.example.practicacountriesapi.ui.viewmodels

import com.example.practicacountriesapi.models.Country

sealed class CountryUiState{
    object  Loading:CountryUiState()
    data class Success( val countries: List<Country>) : CountryUiState()
    data class Error(val message: String): CountryUiState()
}