<?php
include 'db_connect.php';

$username = $_GET['username'];
$topic_id = $_GET['topic_id'];

$user_res = mysqli_query($conn, "SELECT id FROM users WHERE username = '$username'");
$user_id = mysqli_fetch_assoc($user_res)['id'];

$sql = "SELECT l.*, 
        (SELECT COUNT(*) FROM user_history WHERE user_id = '$user_id' AND lesson_id = l.id) as completed_questions,
        (SELECT COUNT(*) FROM exercises WHERE lesson_id = l.id) as total_questions
        FROM lessons l 
        WHERE l.topic_id = '$topic_id' 
        ORDER BY l.lesson_order ASC";

$result = mysqli_query($conn, $sql);
$lessons = array();

$previous_lesson_done = true; // Bài đầu tiên luôn được mở

while($row = mysqli_fetch_assoc($result)) {
    $row['is_locked'] = !$previous_lesson_done;
    
    $is_current_done = ($row['total_questions'] > 0 && $row['completed_questions'] >= $row['total_questions']);
    $previous_lesson_done = $is_current_done;
    
    $lessons[] = $row;
}

echo json_encode(["status" => "success", "data" => $lessons]);
?>