package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import com.example.quizapp.screens.ChapterPicker
import com.example.quizapp.screens.LessonPicker
import com.example.quizapp.screens.NoAccessScreen
import com.example.quizapp.screens.QuizScreen
import com.example.quizapp.screens.TeacherScreen
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {

    // لینک فعلی؛ با تغییر آن، UI دوباره ساخته می‌شود
    private var quizLink by mutableStateOf<QuizLink?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // اگر اپ با لینک باز شده باشد
        quizLink = DeepLinkHandler.parse(intent)
        val lessons = QuestionRepo.load(this)

        setContent {
            QuizAppTheme {
                // اجبار RTL برای تمام رابط کاربری (مستقل از زبان دستگاه)
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    AppRoot(lessons = lessons, link = quizLink)
                }
            }
        }
    }

    // وقتی اپ باز است و لینک جدید می‌آید (launchMode=singleTask)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        quizLink = DeepLinkHandler.parse(intent)
    }
}

@Composable
fun AppRoot(lessons: List<Lesson>, link: QuizLink?) {
    var showTeacher by rememberSaveable { mutableStateOf(false) }
    // با تغییر لینک، انتخاب‌ها ریست می‌شوند
    var lessonIdx by rememberSaveable(link) { mutableStateOf<Int?>(null) }
    var chapterIdx by rememberSaveable(link) { mutableStateOf<Int?>(null) }

    val lesson = lessonIdx?.let { lessons.getOrNull(it) }
    val chapter = chapterIdx?.let { lesson?.chapters?.getOrNull(it) }

    // دکمه بازگشت سیستم
    BackHandler(enabled = showTeacher || lessonIdx != null) {
        when {
            showTeacher -> showTeacher = false
            chapterIdx != null -> chapterIdx = null
            else -> lessonIdx = null
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            when {
                showTeacher -> TeacherScreen(onBack = { showTeacher = false })
                link == null -> NoAccessScreen(onTeacherPanel = { showTeacher = true })
                lesson == null -> LessonPicker(
                    lessons = lessons,
                    onSelect = { lessonIdx = it }
                )
                chapter == null -> ChapterPicker(
                    lesson = lesson,
                    onSelect = { chapterIdx = it }
                )
                else -> QuizScreen(chapter = chapter, quota = link.quota)
            }
        }
    }
}
