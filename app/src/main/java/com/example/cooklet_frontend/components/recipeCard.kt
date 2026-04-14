package com.example.cooklet_frontend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cooklet_frontend.models.Recipe


@Composable
fun RecipeCard(navController: NavController, recipe: Recipe){
    val request = ImageRequest.Builder(LocalContext.current)
        .data(recipe.image)
        .addHeader(
            "User-Agent",
            "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 Chrome/120.0.0.0 Mobile Safari/537.36"
        )
        .addHeader("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8")
        .build()

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = { navController.navigate("RecipeDetails/${recipe._id}")}
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.size(175.dp)
        ) {
            Text(
                recipe.title,
                Modifier.fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                lineHeight = 25.sp,
                fontSize = 25.sp,
                overflow = TextOverflow.Ellipsis
            )
            AsyncImage(
                model = request,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            )
        }
    }
}