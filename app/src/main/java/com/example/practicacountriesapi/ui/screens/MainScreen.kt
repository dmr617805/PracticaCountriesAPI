package com.example.practicacountriesapi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.practicacountriesapi.models.Country
import com.example.practicacountriesapi.ui.navigation.Screen
import com.example.practicacountriesapi.ui.viewmodels.CountryUiState
import com.example.practicacountriesapi.ui.viewmodels.CountryViewModel


@Composable
fun MainScreen(navController: NavController, countryViewModel: CountryViewModel) {

    val uiState by countryViewModel.uiState.collectAsState()

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Detail.route) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, "Add")
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is CountryUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is CountryUiState.Success -> {
                    val countries = (uiState as CountryUiState.Success).countries
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(countries) { country -> CountryItem(country) }
                    }
                }

                is CountryUiState.Error -> {
                    val message = (uiState as CountryUiState.Error).message
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
        }
    }
}


@Composable
fun CountryItem(country: Country) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            country.image?.let { flagUrl ->
                AsyncImage(
                    model = flagUrl,
                    contentDescription = country.name,
                    modifier = Modifier.size(64.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = country.name, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(4.dp))
                val capital = country.capital ?: "N/A"
                Text(text = "Capital: $capital")
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val countryViewModel :CountryViewModel = viewModel()
    MainScreen(navController, countryViewModel )
}