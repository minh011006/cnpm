<?php
session_start();
include 'db_connect.php';

if (isset($_SESSION['admin_logged_in']) && $_SESSION['admin_logged_in'] === true) {
    header("Location: admin_exercise.php");
    exit;
}

$error = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = mysqli_real_escape_string($conn, $_POST['username']);
    $password = $_POST['password'];

    $sql = "SELECT * FROM admins WHERE username = '$username'";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        
        // So khớp mật khẩu đã băm trong database
        if (password_verify($password, $row['password'])) {
            $_SESSION['admin_logged_in'] = true;
            $_SESSION['admin_name'] = $row['full_name'];
            header("Location: admin_exercise.php");
            exit;
        } else {
            $error = "Mật khẩu không chính xác!";
        }
    } else {
        $error = "Tài khoản quản trị không tồn tại!";
    }
}
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập Quản trị hệ thống</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #1a1a2e; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.3); width: 350px; text-align: center; }
        h2 { color: #16213e; margin-bottom: 25px; font-size: 22px; }
        input { width: 100%; padding: 12px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; font-size: 16px; transition: 0.3s; }
        input:focus { border-color: #0f3460; outline: none; box-shadow: 0 0 5px rgba(15,52,96,0.3); }
        button { width: 100%; padding: 12px; background: #0f3460; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 16px; font-weight: bold; transition: 0.3s; }
        button:hover { background: #16213e; transform: translateY(-2px); }
        .error { color: #e94560; margin-bottom: 15px; font-size: 14px; font-weight: bold; }
    </style>
</head>
<body>
    <div class="card">
        <h2>ADMIN LOGIN</h2>
        <?php if ($error != "") echo "<div class='error'>$error</div>"; ?>
        <form method="POST">
            <input type="text" name="username" required placeholder="Tài khoản Admin">
            <input type="password" name="password" required placeholder="Mật khẩu">
            <button type="submit">XÁC NHẬN ĐĂNG NHẬP</button>
        </form>
    </div>
</body>
</html>