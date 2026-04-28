package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.doancoso3.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VocabularyScreen(username: String) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var vocabList by remember { mutableStateOf<List<VocabularyItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitClient.instance.getVocabulary().enqueue(object : Callback<VocabResponse> {
            override fun onResponse(call: Call<VocabResponse>, response: Response<VocabResponse>) {
                vocabList = response.body()?.data ?: emptyList()
                isLoading = false
            }
            override fun onFailure(call: Call<VocabResponse>, t: Throwable) { isLoading = false }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Từ điển thông minh", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        OutlinedTextField(
            value = searchQuery, onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            placeholder = { Text("Tìm kiếm...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1976D2))
        )

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            val filtered = vocabList.filter { it.word.contains(searchQuery, true) || it.meaning_vi.contains(searchQuery, true) }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filtered) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(item.word, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Text("${item.pos} | ${item.phonetic}", fontSize = 14.sp, color = Color.Gray)
                                Text(item.meaning_vi, fontSize = 18.sp, color = Color(0xFF2E7D32))
                            }
                            IconButton(onClick = {
                                RetrofitClient.instance.manageMyVocab("add", username, item.word, item.meaning_vi, item.phonetic ?: "", item.pos ?: "").enqueue(object : Callback<MyVocabResponse> {
                                    override fun onResponse(call: Call<MyVocabResponse>, response: Response<MyVocabResponse>) {
                                        Toast.makeText(context, "Đã lưu Flashcard!", Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onFailure(call: Call<MyVocabResponse>, t: Throwable) {}
                                })
                            }) { Icon(Icons.Default.BookmarkAdd, null, tint = Color(0xFFFF9800)) }
                        }
                    }
                }
            }
        }
    }
}