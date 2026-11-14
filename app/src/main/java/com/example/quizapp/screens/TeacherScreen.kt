package com.example.quizapp.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.DeepLinkHandler
import com.example.quizapp.ui.theme.GreenPrimary
import com.example.quizapp.ui.theme.LinkBlue

/** پنل معلم: تولید و ارسال لینک آزمون */
@Composable
fun TeacherScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    var code by rememberSaveable { mutableStateOf("STU001") }
    var quotaText by rememberSaveable { mutableStateOf("20") }

    val quota = quotaText.toIntOrNull()?.takeIf { it > 0 } ?: DeepLinkHandler.DEFAULT_QUOTA
    val link = DeepLinkHandler.buildLink(code, quota)
    val canSend = code.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) { Text("→ بازگشت") }

        Text("پنل معلم", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("کد دانش‌آموز") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = quotaText,
            onValueChange = { quotaText = it.filter { ch -> ch.isDigit() } },
            label = { Text("تعداد سوال (سهمیه)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))

        Text("لینک آزمون:", color = Color.Gray, fontSize = 13.sp)
        SelectionContainer {
            Text(
                text = link,
                color = LinkBlue,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                // متن پیام ارسالی
                val message = "سلام 👋\n" +
                    "برای شرکت در آزمون روی لینک زیر بزنید:\n" +
                    "$link\n" +
                    "سهمیه شما: $quota سوال\n" +
                    "اگر اپ نصب نیست، ابتدا نصب کنید."
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                context.startActivity(Intent.createChooser(sendIntent, "ارسال لینک به دانش‌آموز"))
            },
            enabled = canSend,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary,
                contentColor = Color.White
            )
        ) {
            Text("📤 ارسال لینک به دانش‌آموز", fontSize = 18.sp)
        }
    }
}
