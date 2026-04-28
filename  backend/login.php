<?php
include 'db_connect.php';

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM users WHERE username = '$username'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    
    if (password_verify($password, $row['password'])) {
        echo json_encode([
            "status" => "success",
            "message" => "Đăng nhập thành công",
            "username" => $row['username'],
            "email" => $row['email']
        ]);
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Sai tài khoản hoặc mật khẩu"
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Sai tài khoản hoặc mật khẩu"
    ]);
}
?>