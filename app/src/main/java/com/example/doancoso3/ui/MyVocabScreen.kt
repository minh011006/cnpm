package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.api.MyVocabItem
import com.example.doancoso3.api.MyVocabResponse
import com.example.doancoso3.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyVocabScreen(username: String) {
    var myVocabs by remember { mutableStateOf<List<MyVocabItem>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Hàm gọi dữ liệu từ MySQL
    fun loadData() {
        RetrofitClient.instance.manageMyVocab(
            action = "get",
            username = username
        ).enqueue(object : Callback<MyVocabResponse> {
            override fun onResponse(call: Call<MyVocabResponse>, response: Response<MyVocabResponse>) {
                myVocabs = response.body()?.data ?: emptyList()
            }
            override fun onFailure(call: Call<MyVocabResponse>, t: Throwable) {
                Toast.makeText(context, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    LaunchedEffect(Unit) { loadData() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF1976D2)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm từ mới", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Từ vựng của tôi",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (myVocabs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Bạn chưa lưu từ vựng nào.", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(myVocabs) { item ->
                        FlashcardItem(item = item)
                    }
                }
            }
        }
    }


    if (showDialog) {
        var wordInput by remember { mutableStateOf("") }
        var meaningInput by remember { mutableStateOf("") }
        var phoneticInput by remember { mutableStateOf("") }
        var posInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Thêm Flashcard mới") },
            text = {
                Column {
                    OutlinedTextField(
                        value = wordInput,
                        onValueChange = { wordInput = it },
                        label = { Text("Từ tiếng Anh (*)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phoneticInput,
                        onValueChange = { phoneticInput = it },
                        label = { Text("Phiên âm (VD: /həˈləʊ/)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = posInput,
                        onValueChange = { posInput = it },
                        label = { Text("Loại từ (VD: Noun, Verb)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = meaningInput,
                        onValueChange = { meaningInput = it },
                        label = { Text("Nghĩa tiếng Việt (*)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (wordInput.isNotEmpty() && meaningInput.isNotEmpty()) {
                            RetrofitClient.instance.manageMyVocab(
                                action = "add",
                                username = username,
                                word = wordInput,
                                meaning = meaningInput,
                                phonetic = phoneticInput,
                                pos = posInput
                            ).enqueue(object : Callback<MyVocabResponse> {
                                override fun onResponse(call: Call<MyVocabResponse>, response: Response<MyVocabResponse>) {
                                    showDialog = false
                                    loadData() // Cập nhật lại danh sách ngay lập tức
                                    Toast.makeText(context, "Đã thêm vào bộ sưu tập!", Toast.LENGTH_SHORT).show()
                                }
                                override fun onFailure(call: Call<MyVocabResponse>, t: Throwable) {
                                    Toast.makeText(context, "Lỗi khi lưu", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } else {
                            Toast.makeText(context, "Vui lòng nhập Từ và Nghĩa!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Lưu lại")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}


@Composable
fun FlashcardItem(item: MyVocabItem) {
    var rotated by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500),
        label = "rotation"
    )
    val isBack = rotation > 90f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { rotated = !rotated }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        colors = CardDefaults.cardColors(
            containerColor = if (isBack) Color(0xFFFFF9C4) else Color(0xFFE3F2FD)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isBack) {

                Column(
                    modifier = Modifier
                        .graphicsLayer { rotationY = 180f }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val posText = item.pos ?: ""
                    val phoneticText = item.phonetic ?: ""
                    val metaText = listOf(posText, phoneticText).filter { it.isNotEmpty() }.joinToString(" | ")

                    if (metaText.isNotEmpty()) {
                        Text(
                            text = metaText,
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Text(
                        text = item.meaning,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD84315)
                    )
                }
            } else {

                Text(
                    text = item.word,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
            }
        }
    }
}