<?php
include 'db_connect.php';

$username = isset($_GET['username']) ? mysqli_real_escape_string($conn, $_GET['username']) : '';
$action = isset($_GET['action']) ? $_GET['action'] : 'get';
$ex_id = isset($_GET['ex_id']) ? (int)$_GET['ex_id'] : 0;
$lesson_id = isset($_GET['lesson_id']) ? (int)$_GET['lesson_id'] : 0;

$user_res = mysqli_query($conn, "SELECT id FROM users WHERE username = '$username'");
$user_data = mysqli_fetch_assoc($user_res);
$user_id = $user_data ? $user_data['id'] : 0;

if ($action == 'clear') {
    mysqli_query($conn, "DELETE FROM user_history WHERE user_id = '$user_id'");
    echo json_encode(["status" => "success", "message" => "Đã reset bài tập"]);
    exit;
}

if ($action == 'save' && $user_id > 0 && $ex_id > 0) {
    $check = mysqli_query($conn, "SELECT id FROM user_history WHERE user_id = '$user_id' AND exercise_id = '$ex_id'");
    if (mysqli_num_rows($check) == 0) {
        mysqli_query($conn, "INSERT INTO user_history (user_id, exercise_id, lesson_id) VALUES ('$user_id', '$ex_id', '$lesson_id')");
    }
    echo json_encode(["status" => "success"]);
    exit;
}

if ($action == 'get' && $lesson_id > 0) {
    $sql = "SELECT * FROM exercises 
            WHERE lesson_id = '$lesson_id' 
            AND id NOT IN (SELECT exercise_id FROM user_history WHERE user_id = '$user_id') 
            ORDER BY id ASC"; 
            
    $result = mysqli_query($conn, $sql);
    $exercises = array();
    while($row = mysqli_fetch_assoc($result)) {
        $exercises[] = $row;
    }
    
    echo json_encode(["status" => "success", "data" => $exercises]);
    exit;
}

echo json_encode(["status" => "error", "message" => "Không tìm thấy dữ liệu"]);
?>