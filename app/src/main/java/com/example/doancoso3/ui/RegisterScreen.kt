package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doancoso3.api.AuthResponse
import com.example.doancoso3.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1976D2), Color(0xFF64B5F6)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("GIA NHẬP KHMT", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Text("Bắt đầu hành trình chinh phục tiếng Anh", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.88f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                OutlinedTextField(
                    value = user, onValueChange = { user = it },
                    label = { Text("Tên đăng nhập") },
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = pass, onValueChange = { pass = it },
                    label = { Text("Mật khẩu") },
                    leadingIcon = { Icon(Icons.Default.Lock, null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        RetrofitClient.instance.register(user, pass, email).enqueue(object : Callback<AuthResponse> {
                            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                                if (response.body()?.status == "success") {
                                    Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                    onNavigateToLogin()
                                } else {
                                    Toast.makeText(context, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {}
                        })
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ĐĂNG KÝ NGAY", fontWeight = FontWeight.Bold)
                }

                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Đã có tài khoản? Đăng nhập", color = Color.Gray)
                }
            }
        }
    }
}