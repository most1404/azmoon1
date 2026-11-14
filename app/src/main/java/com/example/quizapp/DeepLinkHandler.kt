package com.example.quizapp

import android.content.Intent
import android.net.Uri

data class QuizLink(val code: String, val quota: Int)

/** پارس و ساخت لینک: quizapp://start?code=XXX&quota=N */
object DeepLinkHandler {

    const val DEFAULT_QUOTA = 20

    fun parse(intent: Intent?): QuizLink? {
        val uri = intent?.data ?: return null
        if (uri.scheme != "quizapp" || uri.host != "start") return null
        val code = uri.getQueryParameter("code")?.trim().orEmpty()
        if (code.isEmpty()) return null
        val quota = uri.getQueryParameter("quota")
            ?.toIntOrNull()
            ?.takeIf { it > 0 }
            ?: DEFAULT_QUOTA
        return QuizLink(code, quota)
    }

    fun buildLink(code: String, quota: Int): String {
        return "quizapp://start?code=${Uri.encode(code.trim())}&quota=$quota"
    }
}
