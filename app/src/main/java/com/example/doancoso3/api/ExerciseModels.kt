package com.example.doancoso3.api

data class TopicResponse(val status: String, val data: List<TopicItem>)
data class TopicItem(val id: Int, val name: String, val description: String?)

data class LessonResponse(val status: String, val data: List<LessonItem>)
data class LessonItem(val id: Int, val topic_id: Int, val lesson_name: String, val is_locked: Boolean, val completed_questions: Int, val total_questions: Int)

data class ExerciseResponse(val status: String, val data: List<ExerciseItem>)
data class ExerciseItem(val id: Int, val question: String, val option_a: String, val option_b: String, val option_c: String?, val option_d: String?, val correct_answer: String, val explanation: String?, val exercise_type: String?)

data class MyVocabResponse(val status: String, val data: List<MyVocabItem>?)
data class MyVocabItem(
    val id: Int,
    val word: String,
    val phonetic: String?,
    val pos: String?,
    val meaning: String
)