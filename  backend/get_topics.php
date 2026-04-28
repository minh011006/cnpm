<?php
include 'db_connect.php';
$res = mysqli_query($conn, "SELECT * FROM topics");
$data = [];
while($row = mysqli_fetch_assoc($res)) { $data[] = $row; }
echo json_encode(["status" => "success", "data" => $data]);
?>