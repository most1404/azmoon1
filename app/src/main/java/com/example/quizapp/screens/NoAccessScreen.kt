package com.example.quizapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** صفحه‌ای که بدون لینک نمایش داده می‌شود */
@Composable
fun NoAccessScreen(onTeacherPanel: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        TextButton(
            onClick = onTeacherPanel,
            modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
        ) {
            Text("👨‍🏫 پنل معلم")
        }

        Column(
            modifier = Modifier.align(Alignment.Center).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("🎓", fontSize = 96.sp)
            Text(
                "برای شروع آزمون، لینک دریافتی از معلم را باز کنید",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                "لینک باید با quizapp:// شروع شود",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
