package com.example.doancoso3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.doancoso3.ui.*
import com.example.doancoso3.ui.theme.DOANCOSO3Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DOANCOSO3Theme {
                // Quản lý trạng thái màn hình và thông tin đăng nhập
                var currentScreen by remember { mutableStateOf("login") }
                var loggedInUser by remember { mutableStateOf("") }
                var loggedInEmail by remember { mutableStateOf("") }

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // Cấu trúc điều hướng chính của App KHMT
                if (currentScreen == "login") {
                    LoginScreen(
                        onLoginSuccess = { user, email ->
                            loggedInUser = user
                            loggedInEmail = email ?: ""
                            currentScreen = "home"
                        },
                        onNavigateToRegister = { currentScreen = "register" }
                    )
                } else if (currentScreen == "register") {
                    RegisterScreen(
                        onNavigateToLogin = { currentScreen = "login" }
                    )
                } else {
                    // Giao diện sau khi đăng nhập thành công
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(Modifier.height(24.dp))

                                // --- ĐỊNH DANH NHÓM KHMT TRONG MENU ---
                                Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)) {
                                    Text(
                                        text = "KHMT ENGLISH PRO",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF0D47A1) // Xanh Navy KHMT
                                    )
                                    Text(
                                        text = "Version 1.0 - Team KHMT",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }

                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 28.dp))

                                // --- CÁC MỤC MENU ---
                                NavigationDrawerItem(
                                    label = { Text("Trang chủ") },
                                    selected = currentScreen == "home",
                                    onClick = { currentScreen = "home"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.Home, null) }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Từ điển") },
                                    selected = currentScreen == "vocabulary",
                                    onClick = { currentScreen = "vocabulary"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.MenuBook, null) }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Luyện tập") },
                                    selected = currentScreen == "practice",
                                    onClick = { currentScreen = "practice"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.PlayCircle, null) }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Flashcard") },
                                    selected = currentScreen == "myvocab",
                                    onClick = { currentScreen = "myvocab"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.Style, null) }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Hồ sơ cá nhân") },
                                    selected = currentScreen == "profile",
                                    onClick = { currentScreen = "profile"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.Person, null) }
                                )

                                Spacer(Modifier.weight(1f))

                                NavigationDrawerItem(
                                    label = { Text("Đăng xuất", color = Color.Red) },
                                    selected = false,
                                    onClick = { currentScreen = "login"; scope.launch { drawerState.close() } },
                                    icon = { Icon(Icons.Default.Logout, null, tint = Color.Red) }
                                )
                                Spacer(Modifier.height(16.dp))
                            }
                        }
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = when (currentScreen) {
                                                "home" -> "KHMT English"
                                                "vocabulary" -> "Từ Điển"
                                                "practice" -> "Luyện Tập"
                                                "myvocab" -> "Flashcard"
                                                "history" -> "Lịch Sử"
                                                "profile" -> "Hồ Sơ"
                                                else -> "KHMT English"
                                            },
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = Color(0xFFE3F2FD),
                                        titleContentColor = Color(0xFF0D47A1)
                                    )
                                )
                            }
                        ) { paddingValues ->
                            Box(modifier = Modifier.padding(paddingValues)) {
                                when (currentScreen) {
                                    "home" -> HomeScreen(
                                        username = loggedInUser,
                                        onFeatureClick = { screenKey -> currentScreen = screenKey },
                                        onLogout = { currentScreen = "login" }
                                    )
                                    "vocabulary" -> VocabularyScreen(username = loggedInUser)
                                    "practice" -> PracticeScreen(username = loggedInUser)
                                    "myvocab" -> MyVocabScreen(username = loggedInUser)
                                    "history" -> HistoryScreen(username = loggedInUser)
                                    "profile" -> ProfileScreen(
                                        username = loggedInUser,
                                        email = loggedInEmail,
                                        onLogout = { currentScreen = "login" }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}