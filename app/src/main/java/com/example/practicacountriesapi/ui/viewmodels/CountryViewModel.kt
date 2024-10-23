package com.example.practicacountriesapi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicacountriesapi.models.Country
import com.example.practicacountriesapi.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CountryViewModel(
    private val repository: CountryRepository = CountryRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)
    val uiState: StateFlow<CountryUiState> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            try {
                _uiState.value = CountryUiState.Loading
                val countries = repository.getCountries()
                _uiState.value = CountryUiState.Success(countries)

            } catch (e: Exception) {
                _uiState.value =
                    CountryUiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun addCountry(country: Country) {
        viewModelScope.launch {
            try {
                val newCountry = repository.addCountry(country)

                val currentState = _uiState.value
                if (currentState is CountryUiState.Success) {
                    val updatedCountries = currentState.countries + newCountry
                    _uiState.value = CountryUiState.Success(updatedCountries)
                }
            } catch (e: Exception) {
                _uiState.value = CountryUiState.Error(e.localizedMessage ?: "Failed to add country")
            }
        }
    }
}