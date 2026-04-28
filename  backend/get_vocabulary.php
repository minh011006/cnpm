<?php
include 'db_connect.php'; // Đã đổi theo tên file của bạn

$sql = "SELECT * FROM vocabulary ORDER BY id DESC";
$result = mysqli_query($conn, $sql);

$vocabulary = array();

if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {
        $vocabulary[] = $row;
    }
    echo json_encode([
        "status" => "success",
        "data" => $vocabulary
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Không có dữ liệu"
    ]);
}
?>