<?php
include 'db_connect.php';
$username = $_POST['username'] ?? '';
$action = $_POST['action'] ?? '';

$u_res = mysqli_query($conn, "SELECT id FROM users WHERE username = '$username'");
$user_id = ($u_row = mysqli_fetch_assoc($u_res)) ? $u_row['id'] : 0;

if ($user_id == 0) { echo json_encode(["status" => "error"]); exit; }

if ($action == 'add') {
    $word = addslashes($_POST['word']);
    $meaning = addslashes($_POST['meaning']);
    $phonetic = addslashes($_POST['phonetic']);
    $pos = addslashes($_POST['pos']);
    
    $check = mysqli_query($conn, "SELECT id FROM my_vocabulary WHERE user_id = $user_id AND word = '$word'");
    if (mysqli_num_rows($check) == 0) {
        mysqli_query($conn, "INSERT INTO my_vocabulary (user_id, word, phonetic, pos, meaning) 
                            VALUES ($user_id, '$word', '$phonetic', '$pos', '$meaning')");
    }
    echo json_encode(["status" => "success"]); exit;
}

if ($action == 'get') {
    $res = mysqli_query($conn, "SELECT * FROM my_vocabulary WHERE user_id = $user_id ORDER BY id DESC");
    $data = [];
    while($row = mysqli_fetch_assoc($res)) { $data[] = $row; }
    echo json_encode(["status" => "success", "data" => $data]); exit;
}
?>