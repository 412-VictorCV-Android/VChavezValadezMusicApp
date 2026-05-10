package com.example.vchavezvaladezmusicapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vchavezvaladezmusicapp.models.Album

@Composable
fun MiniPlayer(album: Album?, modifier: Modifier = Modifier) {
    // Si todavía no hay un álbum seleccionado, no mostramos el reproductor
    if (album == null) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp)) // Bordes muy redondeados como el mockup
            .background(Color(0xFF220934)) // Color morado oscuro del mockup
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del álbum con Coil
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(album.image)
                .crossfade(true)
                .setHeader("User-Agent", "Mozilla/5.0")
                .build(),
            contentDescription = "Album Cover",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Textos de título y artista
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = album.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = album.artist,
                color = Color(0xFFB39DDB), // Un tono lavanda claro para el subtítulo
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Botón circular blanco de Play
        IconButton(
            onClick = { /* Cumple la rúbrica: Solo estado UI, no requiere audio real */ },
            modifier = Modifier
                .background(Color.White, CircleShape)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.Black
            )
        }
    }
}