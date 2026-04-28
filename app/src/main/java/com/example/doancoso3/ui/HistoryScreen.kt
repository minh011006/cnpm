package com.example.doancoso3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.api.ExerciseItem
import com.example.doancoso3.api.ExerciseResponse
import com.example.doancoso3.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HistoryScreen(username: String) {
    var list by remember { mutableStateOf<List<ExerciseItem>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitClient.instance.getHistory(username).enqueue(object : Callback<ExerciseResponse> {
            override fun onResponse(call: Call<ExerciseResponse>, response: Response<ExerciseResponse>) {
                loading = false
                if (response.isSuccessful) list = response.body()?.data ?: emptyList()
            }
            override fun onFailure(call: Call<ExerciseResponse>, t: Throwable) { loading = false }
        })
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Lịch sử làm bài", fontSize = 20.dp.value.sp, color = Color.Blue)
        if (loading) CircularProgressIndicator()
        else LazyColumn {
            items(list) { item ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(8.dp)) {
                        Text(item.question)
                        Text("Đáp án đúng: ${item.correct_answer}", color = Color(0xFF4CAF50))
                    }
                }
            }
        }
    }
}