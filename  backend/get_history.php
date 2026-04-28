<?php
include 'db_connect.php';
$username = $_GET['username'];
$sql = "SELECT e.* FROM exercises e 
        JOIN user_history h ON e.id = h.exercise_id 
        JOIN users u ON h.user_id = u.id 
        WHERE u.username = '$username' ORDER BY h.created_at DESC";
$res = mysqli_query($conn, $sql);
$data = [];
while($row = mysqli_fetch_assoc($res)) { $data[] = $row; }
echo json_encode(["status" => "success", "data" => $data]);
?>