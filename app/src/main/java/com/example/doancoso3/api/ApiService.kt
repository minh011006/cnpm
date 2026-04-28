package com.example.doancoso3.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("get_vocabulary.php")
    fun getVocabulary(): Call<VocabResponse>


    @GET("get_topics.php")
    fun getTopics(): Call<TopicResponse>

    @GET("get_lessons.php")
    fun getLessons(
        @Query("username") username: String,
        @Query("topic_id") topicId: Int
    ): Call<LessonResponse>


    @GET("get_exercises.php")
    fun getExercisesByLesson(
        @Query("username") username: String,
        @Query("lesson_id") lessonId: Int
    ): Call<ExerciseResponse>

    @GET("get_exercises.php")
    fun saveProgress(
        @Query("username") username: String,
        @Query("action") action: String = "save",
        @Query("ex_id") exId: Int,
        @Query("lesson_id") lessonId: Int
    ): Call<ExerciseResponse>


    @GET("get_history.php")
    fun getHistory(
        @Query("username") username: String
    ): Call<ExerciseResponse>


    @FormUrlEncoded
    @POST("my_vocab_api.php")
    fun manageMyVocab(
        @Field("action") action: String,
        @Field("username") username: String,
        @Field("word") word: String = "",
        @Field("meaning") meaning: String = "",
        @Field("phonetic") phonetic: String = "",
        @Field("pos") pos: String = ""
    ): Call<MyVocabResponse>


    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") pass: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("username") username: String,
        @Field("password") pass: String,
        @Field("email") email: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("change_password.php")
    fun changePassword(
        @Field("username") username: String,
        @Field("old_password") oldPass: String,
        @Field("new_password") newPass: String
    ): Call<AuthResponse>
}