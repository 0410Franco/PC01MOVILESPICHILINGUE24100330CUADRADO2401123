package com.example.uesanapp.presentation.Permisos

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(navController: NavController) {
    val context = LocalContext.current
    var permissionStatus by remember {
        mutableStateOf(
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                "Permiso aceptado"
            } else {
                "Permiso aún pendiente"
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionStatus = if (isGranted) {
            "Permiso concedido"
        } else {
            "Permiso denegado"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permiso de Ubicación") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Estado del permiso:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = permissionStatus,
                style = MaterialTheme.typography.headlineSmall,
                color = when (permissionStatus) {
                    "Permiso concedido" -> MaterialTheme.colorScheme.primary
                    "Permiso denegado" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.secondary
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                enabled = permissionStatus != "Permiso concedido"
            ) {
                Text("Solicitar Permiso")
            }
        }
    }
}
