package com.example.doancoso3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeFeature(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val screenKey: String
)

@Composable
fun HomeScreen(
    username: String,
    onFeatureClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    val features = listOf(
        HomeFeature("Từ điển", Icons.Default.MenuBook, Color(0xFF1976D2), "vocabulary"),
        HomeFeature("Ngữ pháp", Icons.Default.AutoStories, Color(0xFF388E3C), "grammar"),
        HomeFeature("Luyện tập", Icons.Default.PlayCircle, Color(0xFFF57C00), "practice"),
        HomeFeature("Flashcard", Icons.Default.Style, Color(0xFFD32F2F), "myvocab"),
        HomeFeature("Lịch sử", Icons.Default.History, Color(0xFF7B1FA2), "history"),
        HomeFeature("Hồ sơ", Icons.Default.AccountCircle, Color(0xFF455A64), "profile")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // --- PHẦN HEADER: KHMT IDENTITY ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFF0D47A1), Color(0xFF1976D2))),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(top = 24.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "KHMT ENGLISH PRO",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Hệ thống học tập thông minh",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.background(Color.White.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(Icons.Default.Logout, null, tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Chào mừng bạn,", fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f))
                Text(
                    text = username,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // --- PHẦN NỘI DUNG CHÍNH ---
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .offset(y = (-20).dp) // Đẩy Grid lên một chút cho đẹp
        ) {
            Text(
                text = "Bảng điều khiển nhóm KHMT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(features) { feature ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clickable { onFeatureClick(feature.screenKey) },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = feature.color.copy(alpha = 0.12f)
                            ) {
                                Icon(
                                    imageVector = feature.icon,
                                    contentDescription = null,
                                    tint = feature.color,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = feature.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF2D3436)
                            )
                        }
                    }
                }
            }
        }
    }
}