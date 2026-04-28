<?php
session_start();
include 'db_connect.php';

// Kiểm tra đăng nhập Admin (Nếu bạn có làm phần login cho admin)
// if (!isset($_SESSION['admin_logged_in'])) { header("Location: admin_login.php"); exit; }

$message = "";

// 1. XỬ LÝ THÊM TỪ VỰNG
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['add_vocab'])) {
    $word = addslashes($_POST['word']);
    $meaning_vi = addslashes($_POST['meaning_vi']);
    $pos = addslashes($_POST['pos']);
    $phonetic = addslashes($_POST['phonetic']);
    $audio_url = addslashes($_POST['audio_url']);

    // Code chuẩn khớp với các cột trong Database của bạn
    $sql = "INSERT INTO vocabulary (word, meaning_vi, pos, phonetic, audio_url) 
            VALUES ('$word', '$meaning_vi', '$pos', '$phonetic', '$audio_url')";
            
    if (mysqli_query($conn, $sql)) {
        $message = "<div style='color: green; font-weight: bold; margin-bottom: 15px;'>🎉 Thêm từ vựng thành công!</div>";
    } else {
        $message = "<div style='color: red; margin-bottom: 15px;'>Lỗi: " . mysqli_error($conn) . "</div>";
    }
}

// 2. XỬ LÝ XÓA TỪ VỰNG
if (isset($_GET['delete'])) {
    $del_id = (int)$_GET['delete'];
    mysqli_query($conn, "DELETE FROM vocabulary WHERE id = $del_id");
    header("Location: admin_vocab.php");
    exit;
}
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Từ vựng</title>
    <style>
        body { display: flex; font-family: 'Segoe UI', sans-serif; margin: 0; background: #f0f2f5; }
        .sidebar { width: 220px; background: #2c3e50; color: white; height: 100vh; position: fixed; padding: 20px; }
        .main { margin-left: 260px; padding: 30px; width: 100%; box-sizing: border-box; }
        .card { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); margin-bottom: 20px; }
        input { width: 100%; padding: 12px; margin-top: 5px; border: 1px solid #ccc; border-radius: 5px; box-sizing: border-box; }
        button { background: #27ae60; color: white; border: none; padding: 12px 20px; border-radius: 5px; cursor: pointer; font-weight: bold; }
        button:hover { background: #219150; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>VKU English Admin</h2>
        <hr>
        <a href="admin_vocab.php" style="color:white; text-decoration:none; display:block; padding:10px 0;">📚 Quản lý Từ vựng</a>
        <a href="admin_exercise.php" style="color:white; text-decoration:none; display:block; padding:10px 0;">🎯 Lộ trình học tập</a>
    </div>

    <div class="main">
        <div class="card">
            <h3>Thêm Từ vựng mới</h3>
            <?php echo $message; ?>
            <form method="POST">
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
                    <div>
                        <label>Từ vựng (Tiếng Anh):</label>
                        <input type="text" name="word" placeholder="VD: apple" required>
                    </div>
                    <div>
                        <label>Nghĩa (Tiếng Việt):</label>
                        <input type="text" name="meaning_vi" placeholder="VD: quả táo" required>
                    </div>
                    <div>
                        <label>Từ loại:</label>
                        <input type="text" name="pos" placeholder="VD: noun, verb, adj...">
                    </div>
                    <div>
                        <label>Phiên âm:</label>
                        <input type="text" name="phonetic" placeholder="VD: /ˈæp.əl/">
                    </div>
                    <div style="grid-column: span 2;">
                        <label>Link âm thanh (Audio URL - Nếu có):</label>
                        <input type="text" name="audio_url" placeholder="VD: https://link-to-audio.mp3">
                    </div>
                </div>
                <button type="submit" name="add_vocab" style="margin-top: 20px;">+ Lưu Từ Vựng</button>
            </form>
        </div>

        <div class="card">
            <h3>Danh sách Từ vựng hiện có</h3>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Từ vựng</th>
                    <th>Nghĩa (VI)</th>
                    <th>Từ loại</th>
                    <th>Phiên âm</th>
                    <th>Thao tác</th>
                </tr>
                <?php 
                $res = mysqli_query($conn, "SELECT * FROM vocabulary ORDER BY id DESC");
                while($row = mysqli_fetch_assoc($res)) {
                    echo "<tr>
                        <td>{$row['id']}</td>
                        <td style='color: #2980b9; font-weight: bold;'>{$row['word']}</td>
                        <td>{$row['meaning_vi']}</td>
                        <td><i>{$row['pos']}</i></td>
                        <td>{$row['phonetic']}</td>
                        <td><a href='?delete={$row['id']}' style='color:red; text-decoration:none; font-weight: bold;'>Xóa</a></td>
                    </tr>";
                }
                ?>
            </table>
        </div>
    </div>
</body>
</html>