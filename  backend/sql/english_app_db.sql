-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 28, 2026 lúc 09:16 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `english_app_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `admins`
--

INSERT INTO `admins` (`id`, `username`, `password`, `full_name`, `created_at`) VALUES
(2, 'admin', '$2y$10$4b8oU305dLzGWaCJ1rKdkuD5d3FhtY5ePD7kiZ2IVoowUVAzUtRwi', 'Quản trị viên', '2026-04-27 21:51:50');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exercises`
--

CREATE TABLE `exercises` (
  `id` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `exercise_type` varchar(50) NOT NULL,
  `lesson_id` int(11) DEFAULT NULL,
  `question` text NOT NULL,
  `option_a` varchar(255) DEFAULT NULL,
  `option_b` varchar(255) DEFAULT NULL,
  `option_c` varchar(255) DEFAULT NULL,
  `option_d` varchar(255) DEFAULT NULL,
  `correct_answer` varchar(255) NOT NULL,
  `explanation` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `exercises`
--

INSERT INTO `exercises` (`id`, `category`, `exercise_type`, `lesson_id`, `question`, `option_a`, `option_b`, `option_c`, `option_d`, `correct_answer`, `explanation`) VALUES
(1, 'vocabulary', 'vocab_vi_en', NULL, 'Con mèo', 'Cat', 'Dog', 'Cow', 'Buffalo', 'A', NULL),
(2, '', '', 1, '111', '111', '2', '1112', '222', 'A', ''),
(3, '', '', 1, 'd', 'd', 'dde', 'dư', 'dvv', 'B', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lessons`
--

CREATE TABLE `lessons` (
  `id` int(11) NOT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `lesson_name` varchar(255) NOT NULL,
  `lesson_order` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lessons`
--

INSERT INTO `lessons` (`id`, `topic_id`, `lesson_name`, `lesson_order`) VALUES
(1, 1, 'Cơ Bản', 1),
(2, 1, 'Nâng cao', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `my_vocabulary`
--

CREATE TABLE `my_vocabulary` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `word` varchar(255) NOT NULL,
  `phonetic` varchar(100) DEFAULT NULL,
  `pos` varchar(50) DEFAULT NULL,
  `meaning` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `my_vocabulary`
--

INSERT INTO `my_vocabulary` (`id`, `user_id`, `word`, `phonetic`, `pos`, `meaning`) VALUES
(1, 1, 'hi', NULL, NULL, 'xin chào'),
(2, 1, 'No', NULL, NULL, 'không');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topics`
--

CREATE TABLE `topics` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `topics`
--

INSERT INTO `topics` (`id`, `name`, `description`) VALUES
(1, 'Hiện tại đơn', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`) VALUES
(1, 'minh011006', '$2y$10$TAsGvEEt.MQ6yQLqf3wh4OrA.NhfPIm1YfwicHxBR1WYESFksp75K', 'baominh011006@gmail.com', '2026-04-27 17:07:00'),
(2, 'aaa', '$2y$10$Iy5E7qJz97sPCKR2cMPDYuOsST71FtD7ensUumXuSKjQT5U1k5Vuu', 'aaaa', '2026-04-27 21:10:13');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_history`
--

CREATE TABLE `user_history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `exercise_id` int(11) NOT NULL,
  `lesson_id` int(11) DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_history`
--

INSERT INTO `user_history` (`id`, `user_id`, `exercise_id`, `lesson_id`, `is_correct`, `created_at`) VALUES
(3, 1, 2, 1, NULL, '2026-04-28 15:45:27'),
(4, 1, 3, 1, NULL, '2026-04-28 15:45:31');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vocabulary`
--

CREATE TABLE `vocabulary` (
  `id` int(11) NOT NULL,
  `word` varchar(100) NOT NULL,
  `meaning_vi` text NOT NULL,
  `pos` varchar(20) DEFAULT NULL,
  `phonetic` varchar(50) DEFAULT NULL,
  `audio_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vocabulary`
--

INSERT INTO `vocabulary` (`id`, `word`, `meaning_vi`, `pos`, `phonetic`, `audio_url`) VALUES
(1, 'hi', 'xin chào', 'Noun', 'd', '');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `exercises`
--
ALTER TABLE `exercises`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_lesson` (`lesson_id`);

--
-- Chỉ mục cho bảng `lessons`
--
ALTER TABLE `lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Chỉ mục cho bảng `my_vocabulary`
--
ALTER TABLE `my_vocabulary`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `user_history`
--
ALTER TABLE `user_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `exercise_id` (`exercise_id`),
  ADD KEY `fk_history_lesson` (`lesson_id`);

--
-- Chỉ mục cho bảng `vocabulary`
--
ALTER TABLE `vocabulary`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `exercises`
--
ALTER TABLE `exercises`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `lessons`
--
ALTER TABLE `lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `my_vocabulary`
--
ALTER TABLE `my_vocabulary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `topics`
--
ALTER TABLE `topics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `user_history`
--
ALTER TABLE `user_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `vocabulary`
--
ALTER TABLE `vocabulary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `exercises`
--
ALTER TABLE `exercises`
  ADD CONSTRAINT `fk_lesson` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `lessons`
--
ALTER TABLE `lessons`
  ADD CONSTRAINT `lessons_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `my_vocabulary`
--
ALTER TABLE `my_vocabulary`
  ADD CONSTRAINT `my_vocabulary_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `user_history`
--
ALTER TABLE `user_history`
  ADD CONSTRAINT `fk_history_lesson` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_history_ibfk_2` FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
