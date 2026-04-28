<?php
session_start();
include 'db_connect.php';

if (!isset($_SESSION['admin_logged_in']) || $_SESSION['admin_logged_in'] !== true) {
    header("Location: admin_login.php");
    exit;
}

$message = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $new_username = mysqli_real_escape_string($conn, $_POST['username']);
    $full_name = mysqli_real_escape_string($conn, $_POST['full_name']);
    $new_password = $_POST['password'];

    $hashed_password = password_hash($new_password, PASSWORD_DEFAULT);

    $sql = "INSERT INTO admins (username, password, full_name) VALUES ('$new_username', '$hashed_password', '$full_name')";
    
    if (mysqli_query($conn, $sql)) {
        $message = "<div style='color: green; font-weight: bold; margin-bottom: 15px;'>Tạo tài khoản thành công!</div>";
    } else {
        $message = "<div style='color: red; font-weight: bold; margin-bottom: 15px;'>Lỗi: Tên đăng nhập đã tồn tại.</div>";
    }
}
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Admin</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #eef2f3; display: flex; justify-content: center; padding-top: 50px; }
        .card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 400px; text-align: center; }
        input { width: 100%; padding: 12px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #27ae60; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; font-size: 16px; }
        button:hover { background: #2ecc71; }
        a { display: inline-block; margin-top: 20px; text-decoration: none; color: #2980b9; font-weight: bold; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Tạo Tài Khoản Admin Mới</h2>
        <?php echo $message; ?>
        <form method="POST">
            <input type="text" name="username" required placeholder="Tên đăng nhập (VD: giangvien1)">
            <input type="text" name="full_name" required placeholder="Tên hiển thị (VD: Giảng viên A)">
            <input type="password" name="password" required placeholder="Nhập mật khẩu">
            <button type="submit">Cấp Quyền Quản Trị</button>
        </form>
        <a href="admin_exercise.php">&larr; Quay lại trang Bài Tập</a>
    </div>
</body>
</html>