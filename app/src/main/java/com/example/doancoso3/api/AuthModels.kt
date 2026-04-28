package com.example.doancoso3.api


data class AuthResponse(
    val status: String,
    val message: String? = null,
    val username: String? = null, // Thêm dòng này để hứng Tên
    val email: String? = null     // Thêm dòng này để hứng Email
)


data class VocabResponse(
    val status: String,
    val data: List<VocabularyItem>
)

data class VocabularyItem(
    val id: Int,
    val word: String,
    val meaning_vi: String,
    val pos: String,
    val phonetic: String?,
    val audio_url: String?
)



