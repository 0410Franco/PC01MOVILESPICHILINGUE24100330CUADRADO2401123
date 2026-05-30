package com.example.uesanapp.presentation.Destinos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationScreen(navController: NavController) {
    val destinations = listOf(
        Destination(1, "Francia", "París", 1500.0, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?q=80&w=400"),
        Destination(2, "Japón", "Tokio", 2200.0, "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?q=80&w=400"),
        Destination(3, "Italia", "Roma", 1300.0, "https://images.unsplash.com/photo-1552832230-c0197dd311b5?q=80&w=400"),
        Destination(4, "Perú", "Cusco", 800.0, "https://images.unsplash.com/photo-1587595431973-160d0d94add1?q=80&w=400"),
        Destination(5, "España", "Madrid", 1200.0, "https://images.unsplash.com/photo-1539037116277-4db20889f2d4?q=80&w=400")
    )

    val totalCost = destinations.sumOf { it.averageCost }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Destinos") },
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
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(destinations) { destination ->
                    DestinationCard(destination)
                }
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen de todo el catalogo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Total de destinos: ${destinations.size}")
                    Text("Costo acumulado total: $ ${String.format(Locale.US, "%.2f", totalCost)}")
                }
            }
        }
    }
}

@Composable
fun DestinationCard(destination: Destination) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "${destination.city}, ${destination.country}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Costo promedio: $ ${String.format(Locale.US, "%.2f", destination.averageCost)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
//...