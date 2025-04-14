package com.example.mapita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapita.ui.screens.*
import com.example.mapita.ui.theme.MAPITATheme
import com.example.myapplication.screens.CarrerasScreen
import com.example.myapplication.screens.DetalleSalonScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAPITATheme {
                AppWithSplashScreen()
            }
        }
    }
}

@Composable
fun AppWithSplashScreen() {
    var isLoading by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    if (isLoading) {
        SplashScreen()
    } else {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(onLoginSuccess = { navController.navigate("main") })
            }
            composable("main") {
                MainScreen(
                    onLogout = { navController.navigate("login") },
                    navController = navController
                )
            }
            composable("acerca_de") {
                Acerda_de(navController)
            }
            composable("carreras") { CarrerasScreen(navController) }
            composable("edifices") { EdificiosScreen(navController) } // Corregido el nombre aquí
            composable("metalledSalon") { DetalleSalonScreen(navController) }
            composable("soporte") { Soporte(navController) }
            composable("idioma") { Idioma(navController) }
            composable("estacionamiento") {
                Estacionamiento(
                    onBackClick = { navController.popBackStack() },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Iniciar sesión") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    errorMessage = ""
                    when {
                        email.isBlank() || !email.contains("@") -> {
                            errorMessage = "Ingresa un correo válido"
                        }
                        password.length < 8 -> {
                            errorMessage = "La contraseña debe tener al menos 8 caracteres"
                        }
                        else -> {
                            onLoginSuccess()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogout: () -> Unit, navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Opciones", modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.titleMedium)
                Divider()
                NavigationDrawerItem(
                    label = { Text("Soporte Técnico") },
                    selected = false,
                    onClick = { navController.navigate("soporte") }
                )
                NavigationDrawerItem(
                    label = { Text("Acerca de ") },
                    selected = false,
                    onClick = { navController.navigate("acerca_de") }
                )
                NavigationDrawerItem(
                    label = { Text("Idioma ") },
                    selected = false,
                    onClick = { navController.navigate("idioma") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("Instituto Tecnológico de Ags", style = MaterialTheme.typography.titleMedium)
                            Text("Nombre de usuario", style = MaterialTheme.typography.bodySmall)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Acción imagen de perfil */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_perfil),
                                contentDescription = "Imagen de perfil",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { /* Acción para Acceso */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Acceso")
                }

                Button(
                    onClick = { navController.navigate("estacionamiento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Estacionamiento")
                }

                Button(
                    onClick = { navController.navigate("carreras") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Busca tu salón")
                }

                Spacer(modifier = Modifier.weight(1f))

                Divider()

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Salir", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "¡Hola, $name!", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPITATheme {
        Greeting("Android")
    }
}
