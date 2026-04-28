<?php
$host = "localhost";
$user = "root";
$pass = ""; 
$db_name = "english_app_db";

$conn = new mysqli($host, $user, $pass, $db_name);

if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "Lỗi kết nối CSDL"]));
}

$conn->set_charset("utf8");
?>