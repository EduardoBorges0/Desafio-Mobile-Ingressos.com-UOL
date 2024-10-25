package com.app.ingressocom.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.ingressocom.Model.Movie
import com.app.ingressocom.View.ui.theme.IngressocomTheme
import com.app.ingressocom.ViewModel.MoviesViewModel
import com.app.ingressocom.ui.theme.BackgroundMainScreen
import com.app.ingressocom.ui.theme.LettersClickable


@Composable
fun SearchCompose(moviesViewModel: MoviesViewModel, navController: NavController) {
    var search by remember { mutableStateOf("") }
    val movies by moviesViewModel.moviesList.observeAsState(emptyList())


    val filteredMovies = if (search.isNotBlank()) {
        movies.filter { movie ->
            movie.title.contains(search, ignoreCase = true) ||
                    movie.originalTitle.contains(search, ignoreCase = true)
                    || movie.cast.contains(search, ignoreCase = true)
        }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMainScreen)
            .padding(top = 80.dp)
            .padding(6.dp)
    ) {
        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                    tint = LettersClickable
                )
            },
            singleLine = true
        )

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(filteredMovies.size) { index ->
                MoviesComposableInSearch(filteredMovies[index], navController)
            }
        }
    }
}

@Composable
fun MoviesComposableInSearch(movie: Movie, navController: NavController) {
    if (movie.images != null && movie.images.isNotEmpty()) {
        if (movie.premiereDate == null) {
            Column(
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Column (
                    modifier = Modifier
                        .padding(bottom = 7.dp)
                        .clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                ) {
                    AsyncImage(
                        model = movie.images[0].url,
                        contentDescription = "Image of ${movie.title}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp)
                            .size(300.dp)

                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(movie.title, color = Color.White, modifier = Modifier.padding(horizontal = 7.dp))
            }
        } else {
            if(movie.inPreSale){
                val goldGradient = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFD700), // Gold Color
                        Color(0xFFFFC700), // Light Gold
                        Color(0xFFFFA500)  // Amber/Orange for depth
                    )
                )
                Column(
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    Box(
                        modifier = Modifier.clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                    ) {
                        AsyncImage(
                            model = movie.images[0].url,
                            contentDescription = "Image of ${movie.title}",
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    BorderStroke(3.dp, goldGradient), // Applying the gold gradient to the border
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 5.dp)
                                .size(300.dp)

                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .background(goldGradient)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Pré-venda",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        Text(
                            text = "${movie.premiereDate.dayAndMonth}/${movie.premiereDate.year}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .width(193.dp)
                                .padding(bottom = 20.dp)
                                .background(BackgroundMainScreen)
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                    Text(movie.title, color = Color.White, modifier = Modifier.padding(horizontal = 7.dp))
                }
            }else{
                Column(
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    Box(
                        modifier = Modifier.clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                    ) {
                        AsyncImage(
                            model = movie.images[0].url,
                            contentDescription = "Image of ${movie.title}",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 5.dp)
                                .size(300.dp)

                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "${movie.premiereDate.dayAndMonth}/${movie.premiereDate.year}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(start = 0.dp, bottom = 20.dp)
                                .background(BackgroundMainScreen)
                                .padding(horizontal = 0.dp, vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                    Text(movie.title, color = Color.White, modifier = Modifier.padding(horizontal = 7.dp))
                }
            }
        }
    }
    else {
        Column (
            modifier = Modifier.padding(horizontal = 5.dp)
        ){
            Box(
                modifier = Modifier
                    .padding(top = 50.dp)

                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .size(300.dp), // Define um tamanho fixo para o Box
                contentAlignment = Alignment.Center // Centraliza o conteúdo no Box
            ) {
                Text(
                    text = "Poster Indisponível",
                    color = Color.White,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                        .padding(5.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${movie.premiereDate?.dayAndMonth ?: ""}/${movie.premiereDate?.year}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                        .background(BackgroundMainScreen)
                        .padding(3.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            Text(
                text = movie.title,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 8.dp)
            )
        }


    }
}
