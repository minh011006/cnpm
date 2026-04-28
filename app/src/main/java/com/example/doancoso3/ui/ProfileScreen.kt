package com.example.doancoso3.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ProfileScreen(
    username: String,
    email: String,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var showPasswordDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Tài khoản của tôi", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(40.dp))

        ProfileInfoCard(label = "Tên hiển thị", value = username)
        ProfileInfoCard(label = "Email", value = email)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showPasswordDialog = true },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Đổi mật khẩu", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Đăng xuất", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onConfirm = { oldPass, newPass ->

                RetrofitClient.instance.changePassword(username, oldPass, newPass).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            val res = response.body()
                            Toast.makeText(context, res?.message ?: "Phản hồi không rõ", Toast.LENGTH_SHORT).show()
                            if (res?.status == "success") {
                                showPasswordDialog = false // Đóng dialog nếu đổi thành công
                            }
                        }
                    }
                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(context, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        )
    }
}

@Composable
fun ProfileInfoCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 16.sp)
        }
    }
}

@Composable
fun ChangePasswordDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Đổi mật khẩu", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Mật khẩu hiện tại") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Mật khẩu mới") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(oldPassword, newPassword) }) { Text("Cập nhật") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Hủy") }
        }
    )
}