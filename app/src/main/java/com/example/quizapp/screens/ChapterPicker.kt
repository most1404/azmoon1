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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizapp.Lesson

/** انتخاب فصل */
@Composable
fun ChapterPicker(lesson: Lesson, onSelect: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("درس: ${lesson.name}", color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        Text("فصل را انتخاب کنید", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        lesson.chapters.forEachIndexed { index, chapter ->
            Button(
                onClick = { onSelect(index) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(chapter.name)
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
