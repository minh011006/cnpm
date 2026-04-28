<?php
session_start();
include 'db_connect.php';

$message = "";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['add_topic'])) {
        $name = addslashes($_POST['topic_name']);
        if(mysqli_query($conn, "INSERT INTO topics (name) VALUES ('$name')")) 
            $message = "<div style='color:green; font-weight:bold; margin-bottom:10px;'>Tạo Topic thành công!</div>";
    }
    elseif (isset($_POST['add_lesson'])) {
        $t_id = (int)$_POST['topic_id'];
        $l_name = addslashes($_POST['lesson_name']);
        if(mysqli_query($conn, "INSERT INTO lessons (topic_id, lesson_name) VALUES ('$t_id', '$l_name')"))
            $message = "<div style='color:green; font-weight:bold; margin-bottom:10px;'>Tạo Lesson thành công!</div>";
    }
    elseif (isset($_POST['add_exercise'])) {
        $l_id = (int)$_POST['lesson_id'];
        $ques = addslashes($_POST['question']);
        $a = addslashes($_POST['option_a']);
        $b = addslashes($_POST['option_b']);
        $c = addslashes($_POST['option_c']);
        $d = addslashes($_POST['option_d']);
        $ans = addslashes($_POST['correct_answer']);
        $expl = addslashes($_POST['explanation']);

        $sql = "INSERT INTO exercises (lesson_id, question, option_a, option_b, option_c, option_d, correct_answer, explanation) 
                VALUES ('$l_id', '$ques', '$a', '$b', '$c', '$d', '$ans', '$expl')";
        if(mysqli_query($conn, $sql))
            $message = "<div style='color:green; font-weight:bold; margin-bottom:10px;'>Thêm câu hỏi thành công!</div>";
    }
}


if (isset($_GET['del_topic'])) {
    mysqli_query($conn, "DELETE FROM topics WHERE id = " . (int)$_GET['del_topic']);
    header("Location: admin_exercise.php"); exit;
}
if (isset($_GET['del_lesson'])) {
    $topic_id = (int)$_GET['t_id'];
    mysqli_query($conn, "DELETE FROM lessons WHERE id = " . (int)$_GET['del_lesson']);
    header("Location: admin_exercise.php?topic_id=" . $topic_id); exit;
}
if (isset($_GET['del_ex'])) {
    $lesson_id = (int)$_GET['l_id'];
    mysqli_query($conn, "DELETE FROM exercises WHERE id = " . (int)$_GET['del_ex']);
    header("Location: admin_exercise.php?lesson_id=" . $lesson_id); exit;
}


$view = 'topics'; // Mặc định hiển thị danh sách Topic
$current_topic = null;
$current_lesson = null;

if (isset($_GET['lesson_id'])) {
    $view = 'exercises';
    $l_id = (int)$_GET['lesson_id'];
    $l_res = mysqli_query($conn, "SELECT l.*, t.name as topic_name, t.id as topic_id FROM lessons l JOIN topics t ON l.topic_id = t.id WHERE l.id = $l_id");
    $current_lesson = mysqli_fetch_assoc($l_res);
} elseif (isset($_GET['topic_id'])) {
    $view = 'lessons';
    $t_id = (int)$_GET['topic_id'];
    $t_res = mysqli_query($conn, "SELECT * FROM topics WHERE id = $t_id");
    $current_topic = mysqli_fetch_assoc($t_res);
}
?>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Lộ trình học</title>
    <style>
        body { display: flex; font-family: 'Segoe UI', sans-serif; margin: 0; background: #f0f2f5; }
        .sidebar { width: 220px; background: #2c3e50; color: white; height: 100vh; position: fixed; padding: 20px; }
        .main { margin-left: 260px; padding: 30px; width: 100%; box-sizing: border-box; }
        .card { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .breadcrumb { font-size: 18px; margin-bottom: 20px; font-weight: bold; color: #34495e; }
        .breadcrumb a { color: #3498db; text-decoration: none; }
        input, select, textarea { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { background: #3498db; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-weight: bold; }
        button:hover { background: #2980b9; }
        .btn-action { background: #f39c12; padding: 6px 12px; border-radius: 4px; color: white; text-decoration: none; font-size: 14px; }
        .btn-delete { color: #e74c3c; text-decoration: none; font-weight: bold; margin-left: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background: #f8f9fa; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>VKU English</h2>
        <hr>
        <a href="admin_vocab.php" style="color:white; text-decoration:none; display:block; padding:10px 0;">📚 Quản lý Từ vựng</a>
        <a href="admin_exercise.php" style="color:white; text-decoration:none; display:block; padding:10px 0;">🎯 Lộ trình học tập</a>
    </div>

    <div class="main">
        <?php echo $message; ?>

        <div class="breadcrumb">
            <?php if ($view == 'topics'): ?>
                📍 Danh sách Chủ đề (Topics)
            <?php elseif ($view == 'lessons'): ?>
                <a href="admin_exercise.php">📍 Lộ trình học</a> > Chủ đề: <span style="color:#2ecc71;"><?php echo $current_topic['name']; ?></span>
            <?php elseif ($view == 'exercises'): ?>
                <a href="admin_exercise.php">📍 Lộ trình học</a> > 
                <a href="admin_exercise.php?topic_id=<?php echo $current_lesson['topic_id']; ?>"><?php echo $current_lesson['topic_name']; ?></a> > 
                Bài học: <span style="color:#e67e22;"><?php echo $current_lesson['lesson_name']; ?></span>
            <?php endif; ?>
        </div>

        <?php 
       
        if ($view == 'topics'): 
        ?>
            <div class="card" style="background: #ebf5fb;">
                <h3>+ Tạo Chủ đề mới</h3>
                <form method="POST">
                    <input name="topic_name" placeholder="Tên chủ đề (VD: Ngữ pháp cơ bản)" required>
                    <button name="add_topic">Tạo Chủ đề</button>
                </form>
            </div>
            <div class="card">
                <table>
                    <tr><th>ID</th><th>Tên Chủ đề</th><th>Thao tác</th></tr>
                    <?php 
                    $res = mysqli_query($conn, "SELECT * FROM topics ORDER BY id DESC");
                    while($row = mysqli_fetch_assoc($res)) {
                        echo "<tr>
                            <td>{$row['id']}</td>
                            <td style='font-weight:bold; font-size:16px;'>{$row['name']}</td>
                            <td>
                                <a href='?topic_id={$row['id']}' class='btn-action'>👉 Quản lý Bài học</a>
                                <a href='?del_topic={$row['id']}' class='btn-delete' onclick=\"return confirm('Xóa chủ đề sẽ xóa toàn bộ bài học bên trong. Chắc chắn xóa?');\">Xóa</a>
                            </td>
                        </tr>";
                    }
                    ?>
                </table>
            </div>

        <?php 

        elseif ($view == 'lessons'): 
        ?>
            <div class="card" style="background: #e8f8f5;">
                <h3>+ Thêm Bài học vào Chủ đề này</h3>
                <form method="POST">
                    <input type="hidden" name="topic_id" value="<?php echo $current_topic['id']; ?>">
                    <input name="lesson_name" placeholder="Tên bài học (VD: Bài 1: Thì hiện tại đơn)" required>
                    <button name="add_lesson" style="background:#2ecc71;">Thêm Bài học</button>
                </form>
            </div>
            <div class="card">
                <table>
                    <tr><th>ID</th><th>Tên Bài học</th><th>Thao tác</th></tr>
                    <?php 
                    $res = mysqli_query($conn, "SELECT * FROM lessons WHERE topic_id = $t_id ORDER BY id ASC");
                    while($row = mysqli_fetch_assoc($res)) {
                        echo "<tr>
                            <td>{$row['id']}</td>
                            <td style='font-weight:bold;'>{$row['lesson_name']}</td>
                            <td>
                                <a href='?lesson_id={$row['id']}' class='btn-action' style='background:#e67e22;'>📝 Quản lý Câu hỏi</a>
                                <a href='?del_lesson={$row['id']}&t_id={$t_id}' class='btn-delete'>Xóa</a>
                            </td>
                        </tr>";
                    }
                    ?>
                </table>
            </div>

        <?php 
     
        elseif ($view == 'exercises'): 
        ?>
            <div class="card" style="background: #fdf2e9;">
                <h3>+ Thêm Câu hỏi vào Bài học này</h3>
                <form method="POST">
                    <input type="hidden" name="lesson_id" value="<?php echo $current_lesson['id']; ?>">
                    <textarea name="question" placeholder="Nội dung câu hỏi..." required rows="2"></textarea>
                    <div style="display:grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                        <input name="option_a" placeholder="Đáp án A" required>
                        <input name="option_b" placeholder="Đáp án B" required>
                        <input name="option_c" placeholder="Đáp án C">
                        <input name="option_d" placeholder="Đáp án D">
                    </div>
                    <input name="correct_answer" placeholder="Đáp án đúng (A, B, C, hoặc D)" required>
                    <textarea name="explanation" placeholder="Giải thích đáp án..." rows="2" style="background:#fffde7;"></textarea>
                    <button name="add_exercise" style="background:#e67e22;">Lưu Câu hỏi</button>
                </form>
            </div>
            <div class="card">
                <table>
                    <tr><th>Câu hỏi</th><th>Đáp án</th><th>Giải thích</th><th>Xóa</th></tr>
                    <?php 
                    $res = mysqli_query($conn, "SELECT * FROM exercises WHERE lesson_id = $l_id ORDER BY id DESC");
                    while($row = mysqli_fetch_assoc($res)) {
                        echo "<tr>
                            <td>" . substr($row['question'], 0, 50) . "...</td>
                            <td style='color:green; font-weight:bold;'>{$row['correct_answer']}</td>
                            <td><i>" . substr($row['explanation'], 0, 40) . "...</i></td>
                            <td><a href='?del_ex={$row['id']}&l_id={$l_id}' class='btn-delete'>Xóa</a></td>
                        </tr>";
                    }
                    ?>
                </table>
            </div>
        <?php endif; ?>

    </div>
</body>
</html>