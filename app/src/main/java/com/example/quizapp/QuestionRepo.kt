package com.example.quizapp

import android.content.Context
import org.json.JSONObject

data class Question(val q: String, val a: String)
data class Chapter(val name: String, val questions: List<Question>)
data class Lesson(val name: String, val chapters: List<Chapter>)

/** بارگذاری سوالات از assets/questions.json */
object QuestionRepo {

    fun load(context: Context): List<Lesson> {
        return try {
            val text = context.assets.open("questions.json")
                .bufferedReader(Charsets.UTF_8)
                .use { it.readText() }
            val lessonsArray = JSONObject(text).getJSONArray("lessons")

            val lessons = mutableListOf<Lesson>()
            for (i in 0 until lessonsArray.length()) {
                val lessonObj = lessonsArray.getJSONObject(i)
                val chaptersArray = lessonObj.getJSONArray("chapters")
                val chapters = mutableListOf<Chapter>()
                for (j in 0 until chaptersArray.length()) {
                    val chapterObj = chaptersArray.getJSONObject(j)
                    val questionsArray = chapterObj.getJSONArray("questions")
                    val questions = mutableListOf<Question>()
                    for (k in 0 until questionsArray.length()) {
                        val qObj = questionsArray.getJSONObject(k)
                        questions.add(Question(qObj.getString("q"), qObj.getString("a")))
                    }
                    chapters.add(Chapter(chapterObj.getString("chapterName"), questions))
                }
                lessons.add(Lesson(lessonObj.getString("lessonName"), chapters))
            }
            lessons
        } catch (e: Exception) {
            // اگر فایل خراب یا ناموجود بود، لیست خالی برمی‌گردد
            emptyList()
        }
    }
}
