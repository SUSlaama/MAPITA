package com.example.mapita.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mapita.R

@Composable
fun Estacionamiento(onBackClick: () -> Unit, navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Zona 1", "Zona 2", "Zona 3", "Zona 4", "Zona 5")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0C29), Color(0xFF302B63), Color(0xFF24243E))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
                )
            }
        }

        // Este Box hará que el contenido de zona se ajuste automáticamente al espacio disponible
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> Zone1Screen()
                1 -> ZoneWithImageScreen("zona2")
                2 -> ZoneWithImageScreen("zona3")
                3 -> ZoneWithImageScreen("zona4")
                4 -> ZoneWithImageScreen("zona5")
            }
        }

        // Botón inferior
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(160.dp)
        ) {
            Text("Regresar", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Zone1Screen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.DirectionsCar,
            contentDescription = "Estacionamiento",
            modifier = Modifier.size(120.dp),
            tint = Color(0xFFFFA726)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Estacionamiento", fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun ZoneWithImageScreen(imageName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val imageId = when (imageName) {
            "zona2" -> R.drawable.zona2
            "zona3" -> R.drawable.zona3
            "zona4" -> R.drawable.zona4
            "zona5" -> R.drawable.zona5
            else -> R.drawable.zona2
        }

        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Mapa de $imageName",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Espacios libres", color = Color.White, fontSize = 16.sp)
    }
}