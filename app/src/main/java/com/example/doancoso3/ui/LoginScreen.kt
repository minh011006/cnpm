package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
fun LoginScreen(
    onLoginSuccess: (String, String?) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0D47A1), Color(0xFF1976D2)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // LOGO KHMT
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.2f)
        ) {
            Icon(Icons.Default.School, null, tint = Color.White, modifier = Modifier.padding(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("KHMT ENGLISH", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Text("Hệ thống học tập nhóm KHMT", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.88f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Đăng nhập", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = username, onValueChange = { username = it },
                    label = { Text("Tên đăng nhập") },
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    label = { Text("Mật khẩu") },
                    leadingIcon = { Icon(Icons.Default.Lock, null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        RetrofitClient.instance.login(username, password).enqueue(object : Callback<AuthResponse> {
                            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                                if (response.body()?.status == "success") {
                                    onLoginSuccess(username, response.body()?.email)
                                } else {
                                    Toast.makeText(context, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                                Toast.makeText(context, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show()
                            }
                        })
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ĐĂNG NHẬP", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                ) {
                    Text("Chưa có tài khoản? Đăng ký ngay", color = Color(0xFF1976D2))
                }
            }
        }
    }
}