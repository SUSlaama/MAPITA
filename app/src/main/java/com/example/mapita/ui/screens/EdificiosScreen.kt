package com.example.mapita.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mapita.R
import com.example.myapplication.components.TopBar


@Composable
fun EdificiosScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A237E))
    ) {
        TopBar(navController = navController, showSearchBar = true)

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp)),
                tonalElevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = Color.White.copy(alpha = 0.9f)
            ) {
                Text(
                    text = "Industrial",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E),
                    textAlign = TextAlign.Center
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                tonalElevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.85f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Mapa del campus",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val edificios = mapOf(
                    "Edificio L-2" to listOf("Salón 22A", "Salón 22B", "Salón 22C"),
                    "Edificio P" to listOf("Salón 23A", "Salón 23B"),
                    "Edificio M" to listOf("Salón 24A", "Salón 24B", "Salón 24C", "Salón 24D")
                )

                edificios.forEach { (edificio, salones) ->
                    EdificioDesplegable(
                        nombre = edificio,
                        salones = salones,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun EdificioDesplegable(nombre: String, salones: List<String>, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = if (expanded) 6.dp else 3.dp, shape = RoundedCornerShape(12.dp)),
            tonalElevation = if (expanded) 6.dp else 3.dp,
            shape = RoundedCornerShape(12.dp),
            color = Color.White.copy(alpha = 0.85f)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = nombre,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1A237E).copy(alpha = 0.9f)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = if (expanded) "Contraer" else "Expandir",
                        tint = Color(0xFF1A237E).copy(alpha = 0.7f)
                    )
                }

                if (expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        salones.forEach { salon ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                                    .clickable {
                                        navController.navigate("metalledSalon") // <- Navegación al detalle del salón
                                    },
                                tonalElevation = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.7f)
                            ) {
                                Text(
                                    text = salon,
                                    modifier = Modifier
                                        .padding(vertical = 12.dp, horizontal = 16.dp),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF1A237E).copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EdificiosScreenPreview() {
    MaterialTheme {
        EdificiosScreen(navController = rememberNavController())
    }
}
