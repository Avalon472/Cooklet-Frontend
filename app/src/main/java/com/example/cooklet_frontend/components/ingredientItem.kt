package com.example.cooklet_frontend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun IngredientItem(item: String, quantity: Number, unit: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        val opacity = if(isChecked) 0.5f else 1f

        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        Row(modifier = Modifier
            .fillMaxWidth()
            .alpha(opacity),
            horizontalArrangement = Arrangement.SpaceBetween) {
            val isCompleted = if (isChecked) {
                TextStyle(
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                TextStyle.Default
            }

            Text(item, style = isCompleted, color = MaterialTheme.colorScheme.onSurface)
            Text("$quantity $unit", style = isCompleted, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}