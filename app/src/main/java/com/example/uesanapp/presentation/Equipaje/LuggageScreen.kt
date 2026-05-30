package com.example.uesanapp.presentation.Equipaje

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuggageScreen(navController: NavController) {
    var weightInput by remember { mutableStateOf("") }
    var flightType by remember { mutableStateOf("Nacional") }
    var resultText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val options = listOf("Nacional", "Internacional")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Equipaje") },
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
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text("Peso de maleta (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorText.isNotEmpty()
            )
            if (errorText.isNotEmpty()) {
                Text(text = errorText, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tipo de vuelo:", style = MaterialTheme.typography.titleMedium)
            Column(Modifier.selectableGroup()) {
                options.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == flightType),
                                onClick = { flightType = text },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == flightType),
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
                    val weight = weightInput.toDoubleOrNull()
                    if (weight == null || weight <= 0) {
                        errorText = "Ingrese un número válido mayor a cero"
                        resultText = ""
                    } else {
                        errorText = ""
                        val limit = if (flightType == "Nacional") 23.0 else 32.0
                        val exceeded = weight > limit
                        val diff = if (exceeded) weight - limit else 0.0
                        
                        resultText = """
                            Equipaje permitido: $limit kg
                            Excedido: ${if (exceeded) "SÍ" else "NO"}
                            Kg excedidos: ${String.format("%.2f", diff)} kg
                        """.trimIndent()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
