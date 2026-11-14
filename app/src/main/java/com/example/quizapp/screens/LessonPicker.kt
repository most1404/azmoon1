package com.example.quizapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizapp.Lesson

/** انتخاب درس */
@Composable
fun LessonPicker(lessons: List<Lesson>, onSelect: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("درس را انتخاب کنید", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (lessons.isEmpty()) {
            Text("هیچ درسی یافت نشد. فایل questions.json را بررسی کنید.")
        }

        lessons.forEachIndexed { index, lesson ->
            Button(
                onClick = { onSelect(index) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(lesson.name)
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
