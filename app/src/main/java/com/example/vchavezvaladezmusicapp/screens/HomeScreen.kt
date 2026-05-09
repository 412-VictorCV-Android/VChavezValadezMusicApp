package com.example.vchavezvaladezmusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vchavezvaladezmusicapp.models.Album
import com.example.vchavezvaladezmusicapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onAlbumSelected: (Album) -> Unit
) {
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.instance.getAlbums()
            }
            albums = response
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo al descargar: ${e.message}")
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF0E6FF))) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF7B42F6))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item { HomeHeader() }

                item {
                    SectionTitle(title = "Albums", onSeeMore = { })
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(albums) { album ->
                            AlbumLargeCard(album = album, onClick = {
                                onAlbumSelected(album)
                                onNavigateToDetail(album.id)
                            })
                        }
                    }
                }

                item {
                    SectionTitle(title = "Recently Played", onSeeMore = { })
                }

                items(albums) { album ->
                    RecentlyPlayedCard(album = album, onClick = {
                        onAlbumSelected(album)
                        onNavigateToDetail(album.id)
                    })
                }
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(140.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF7B42F6))
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Good Morning!", color = Color.White, fontSize = 14.sp)
            Text("Victor Chavez", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionTitle(title: String, onSeeMore: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2A2A2A))
        Text(text = "See more", color = Color(0xFF7B42F6), fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.clickable { onSeeMore() })
    }
}

@Composable
fun AlbumLargeCard(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = album.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xCC220934))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(album.title, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(album.artist, color = Color.LightGray, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.Black,
                        modifier = Modifier.background(Color.White, CircleShape).padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecentlyPlayedCard(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = album.image,
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = album.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "${album.artist} • Popular Song", color = Color.Gray, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
        }
    }
}