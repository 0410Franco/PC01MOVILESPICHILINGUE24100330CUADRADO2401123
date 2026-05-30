package com.example.uesanapp.presentation.Presupuesto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController) {
    var daysInput by remember { mutableStateOf("") }
    var dailyBudgetInput by remember { mutableStateOf("") }
    var accommodationType by remember { mutableStateOf("Estándar") }
    var totalResult by remember { mutableStateOf("") }
    
    val accommodationOptions = mapOf(
        "Económico" to 0.8,
        "Estándar" to 1.0,
        "Premium" to 1.5
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Presupuesto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = daysInput,
                onValueChange = { daysInput = it },
                label = { Text("Número de días") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = dailyBudgetInput,
                onValueChange = { dailyBudgetInput = it },
                label = { Text("Presupuesto diario ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tipo de alojamiento:", style = MaterialTheme.typography.titleMedium)
            Column(Modifier.selectableGroup()) {
                accommodationOptions.keys.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .selectable(
                                selected = (text == accommodationType),
                                onClick = { accommodationType = text },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == accommodationType),
                            onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val days = daysInput.toIntOrNull()
                    val dailyBudget = dailyBudgetInput.toDoubleOrNull()
                    val factor = accommodationOptions[accommodationType] ?: 1.0
                    
                    if (days != null && dailyBudget != null && days > 0 && dailyBudget > 0) {
                        val total = days * dailyBudget * factor
                        totalResult = String.format(Locale.US, "Total aproximado: $ %.2f", total)
                    } else {
                        totalResult = "Por favor, ingrese valores mayores a cero"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular el total")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (totalResult.isNotEmpty()) {
                Text(
                    text = totalResult,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
