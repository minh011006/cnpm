package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PracticeScreen(username: String) {
    var currentStep by remember { mutableStateOf("TOPICS") } // TOPICS, LESSONS, QUIZ
    var selectedTopicId by remember { mutableIntStateOf(0) }
    var selectedLessonId by remember { mutableIntStateOf(0) }

    when (currentStep) {
        "TOPICS" -> TopicListScreen(
            onTopicClick = { id -> selectedTopicId = id; currentStep = "LESSONS" }
        )
        "LESSONS" -> LessonListScreen(
            username = username,
            topicId = selectedTopicId,
            onBack = { currentStep = "TOPICS" },
            onLessonSelected = { id -> selectedLessonId = id; currentStep = "QUIZ" }
        )
        "QUIZ" -> ExerciseQuizScreen(
            username = username,
            lessonId = selectedLessonId,
            onBack = { currentStep = "LESSONS" }
        )
    }
}

@Composable
fun TopicListScreen(onTopicClick: (Int) -> Unit) {
    var topics by remember { mutableStateOf<List<TopicItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitClient.instance.getTopics().enqueue(object : Callback<TopicResponse> {
            override fun onResponse(call: Call<TopicResponse>, response: Response<TopicResponse>) {
                topics = response.body()?.data ?: emptyList()
                isLoading = false
            }
            override fun onFailure(call: Call<TopicResponse>, t: Throwable) { isLoading = false }
        })
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chọn chủ đề học tập", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
        Spacer(Modifier.height(16.dp))
        if (isLoading) CircularProgressIndicator()
        else LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(topics) { topic ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onTopicClick(topic.id) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFF1976D2))
                        Spacer(Modifier.width(16.dp))
                        Text(topic.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun LessonListScreen(username: String, topicId: Int, onBack: () -> Unit, onLessonSelected: (Int) -> Unit) {
    var lessons by remember { mutableStateOf<List<LessonItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(topicId) {
        RetrofitClient.instance.getLessons(username, topicId).enqueue(object : Callback<LessonResponse> {
            override fun onResponse(call: Call<LessonResponse>, response: Response<LessonResponse>) {
                lessons = response.body()?.data ?: emptyList()
                isLoading = false
            }
            override fun onFailure(call: Call<LessonResponse>, t: Throwable) { isLoading = false }
        })
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
            Text("Danh sách bài học", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        if (isLoading) CircularProgressIndicator()
        else LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(lessons) { lesson ->
                val isLocked = lesson.is_locked
                Card(
                    modifier = Modifier.fillMaxWidth().clickable(enabled = !isLocked) { onLessonSelected(lesson.id) },
                    colors = CardDefaults.cardColors(containerColor = if (isLocked) Color(0xFFE0E0E0) else Color.White)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.PlayCircle,
                            contentDescription = null,
                            tint = if (isLocked) Color.Gray else Color(0xFF4CAF50)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(Modifier.weight(1f)) {
                            Text(lesson.lesson_name, fontWeight = FontWeight.Bold, color = if (isLocked) Color.Gray else Color.Black)
                            // Thanh tiến độ bài học
                            val progress = if (lesson.total_questions > 0) lesson.completed_questions.toFloat() / lesson.total_questions else 0f
                            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().padding(top = 4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseQuizScreen(username: String, lessonId: Int, onBack: () -> Unit) {
    var exercises by remember { mutableStateOf<List<ExerciseItem>>(emptyList()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showExplanation by remember { mutableStateOf(false) }

    LaunchedEffect(lessonId) {
        RetrofitClient.instance.getExercisesByLesson(username, lessonId).enqueue(object : Callback<ExerciseResponse> {
            override fun onResponse(call: Call<ExerciseResponse>, response: Response<ExerciseResponse>) {
                exercises = response.body()?.data ?: emptyList()
            }
            override fun onFailure(call: Call<ExerciseResponse>, t: Throwable) {}
        })
    }

    if (exercises.isEmpty()) return

    val current = exercises[currentIndex]

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Câu ${currentIndex + 1}/${exercises.size}", color = Color.Gray)
        Spacer(Modifier.height(10.dp))
        Text(current.question, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        val options = listOf("A" to current.option_a, "B" to current.option_b, "C" to current.option_c, "D" to current.option_d)
        options.filter { it.second != null }.forEach { (letter, text) ->
            val isCorrect = letter == current.correct_answer.trim().uppercase()
            val color = when {
                selectedAnswer == null -> MaterialTheme.colorScheme.surfaceVariant
                isCorrect -> Color(0xFF4CAF50)
                selectedAnswer == letter -> Color.Red
                else -> MaterialTheme.colorScheme.surfaceVariant
            }

            Button(
                onClick = {
                    if (selectedAnswer == null) {
                        selectedAnswer = letter
                        showExplanation = true
                        // Lưu tiến trình lên server
                        RetrofitClient.instance.saveProgress(username, "save", current.id, lessonId).enqueue(object : Callback<ExerciseResponse>{
                            override fun onResponse(call: Call<ExerciseResponse>, response: Response<ExerciseResponse>) {}
                            override fun onFailure(call: Call<ExerciseResponse>, t: Throwable) {}
                        })
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) { Text("$letter. $text") }
        }

        AnimatedVisibility(visible = showExplanation) {
            Card(modifier = Modifier.padding(top = 16.dp).fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))) {
                Column(Modifier.padding(16.dp)) {
                    Text("Giải thích:", fontWeight = FontWeight.Bold, color = Color(0xFFFBC02D))
                    Text(current.explanation ?: "Không có giải thích.")
                    Button(
                        onClick = {
                            if (currentIndex < exercises.size - 1) { currentIndex++; selectedAnswer = null; showExplanation = false }
                            else { onBack() }
                        },
                        modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                    ) { Text("Tiếp tục") }
                }
            }
        }
    }
}