<?php
include 'db_connect.php';

$username = $_POST['username'];
$old_password = $_POST['old_password'];
$new_password = $_POST['new_password'];

// Tìm user trong database
$sql = "SELECT * FROM users WHERE username = '$username'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    
    if (password_verify($old_password, $row['password'])) {
        
        $hashed_new_password = password_hash($new_password, PASSWORD_DEFAULT);
        
        $update_sql = "UPDATE users SET password = '$hashed_new_password' WHERE username = '$username'";
        
        if (mysqli_query($conn, $update_sql)) {
            echo json_encode([
                "status" => "success",
                "message" => "Đổi mật khẩu thành công!"
            ]);
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Lỗi cập nhật CSDL"
            ]);
        }
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Mật khẩu hiện tại không đúng!"
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Lỗi: Không tìm thấy tài khoản"
    ]);
}
?>