package com.example.practicacountriesapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.practicacountriesapi.R
import com.example.practicacountriesapi.models.Country
import com.example.practicacountriesapi.ui.viewmodels.CountryViewModel


@Composable
fun DetailScreen(navController: NavController, countryViewModel: CountryViewModel) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    var countryState by remember { mutableStateOf(Country(name = "", capital = "", image = "")) }
    val uiState by countryViewModel.uiState.collectAsState()

    fun updateCountry(name: String, capital: String?, image: String?) {
        countryState = countryState.copy(name = name, capital = capital, image = image)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Add")
            }
        }
    ) { innerPadding ->

        Card(
            modifier = Modifier.padding(innerPadding),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = countryState.name,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") },
                    onValueChange = { newName ->
                        updateCountry(
                            newName,
                            countryState.capital,
                            countryState.image
                        )
                    }
                )

                countryState.capital?.let {
                    OutlinedTextField(
                        value = it,
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Capital") },
                        onValueChange = { newCapital ->
                            updateCountry(
                                countryState.name,
                                newCapital,
                                countryState.image
                            )
                        }
                    )
                }

                countryState.image?.let {
                    OutlinedTextField(
                        value = it,
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Image") },
                        onValueChange = { newImage ->
                            updateCountry(
                                countryState.name,
                                countryState.capital,
                                newImage
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        countryViewModel.addCountry(countryState)
                        navController.popBackStack()
                              },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    Text("Save")
                }
            }

        }


    }


}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    val countryViewModel :CountryViewModel = viewModel()
    DetailScreen(navController, countryViewModel )
}