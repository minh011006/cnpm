<?php
require 'db_connect.php'; 

header('Content-Type: application/json; charset=utf-8'); 

$username = isset($_POST['username']) ? trim($_POST['username']) : '';
$password = isset($_POST['password']) ? trim($_POST['password']) : '';
$email = isset($_POST['email']) ? trim($_POST['email']) : '';

if (empty($username) || empty($password)) {
    echo json_encode(["status" => "error", "message" => "Vui lòng nhập đủ thông tin!"]);
    exit;
}

$stmt = $conn->prepare("SELECT id FROM users WHERE username = ?");
$stmt->bind_param("s", $username);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    echo json_encode(["status" => "error", "message" => "Tên đăng nhập đã tồn tại!"]);
} else {
    $hashed_password = password_hash($password, PASSWORD_DEFAULT);

    $insert_stmt = $conn->prepare("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
    $insert_stmt->bind_param("sss", $username, $hashed_password, $email);

    if ($insert_stmt->execute()) {
        echo json_encode(["status" => "success", "message" => "Đăng ký thành công!"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Có lỗi xảy ra, vui lòng thử lại!"]);
    }
    $insert_stmt->close();
}

$stmt->close();
$conn->close();
?>