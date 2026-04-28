<?php
include 'db_connect.php';

$word = $_POST['word'];
$meaning_vi = $_POST['meaning_vi'];
$pos = $_POST['pos'];
$phonetic = $_POST['phonetic'];

$audio_url = "";
if(!empty($_FILES['audio_file']['name'])){
    $target_dir = "uploads/";
    $file_name = time() . "_" . basename($_FILES["audio_file"]["name"]);
    $target_file = $target_dir . $file_name;
    
    if(move_uploaded_file($_FILES["audio_file"]["tmp_name"], $target_file)){
        $audio_url = $file_name;
    }
}

$sql = "INSERT INTO vocabulary (word, meaning_vi, pos, phonetic, audio_url) 
        VALUES ('$word', '$meaning_vi', '$pos', '$phonetic', '$audio_url')";

if (mysqli_query($conn, $sql)) {
    echo "<script>alert('Thêm từ mới thành công!'); window.location.href='admin_add.php';</script>";
} else {
    echo "Lỗi: " . mysqli_error($conn);
}
?>