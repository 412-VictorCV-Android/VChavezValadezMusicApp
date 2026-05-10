package com.example.vchavezvaladezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.vchavezvaladezmusicapp.components.MiniPlayer
import com.example.vchavezvaladezmusicapp.models.Album
import com.example.vchavezvaladezmusicapp.screens.DetailScreen
import com.example.vchavezvaladezmusicapp.screens.HomeScreen
import kotlinx.serialization.Serializable
import com.example.vchavezvaladezmusicapp.ui.theme.VChavezValadezMusicAppTheme

@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val id: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VChavezValadezMusicAppTheme {
                val navController = rememberNavController()
                var currentPlayingAlbum by remember { mutableStateOf<Album?>(null) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = HomeRoute
                        ) {
                            composable<HomeRoute> {
                                HomeScreen(
                                    onNavigateToDetail = { id ->
                                        navController.navigate(DetailRoute(id = id))
                                    },
                                    onAlbumSelected = { album ->
                                        currentPlayingAlbum = album
                                    }
                                )
                            }

                            composable<DetailRoute> { backStackEntry ->
                                val detailRoute: DetailRoute = backStackEntry.toRoute()
                                DetailScreen(
                                    id = detailRoute.id,
                                    onNavigateBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        MiniPlayer(
                            album = currentPlayingAlbum,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}