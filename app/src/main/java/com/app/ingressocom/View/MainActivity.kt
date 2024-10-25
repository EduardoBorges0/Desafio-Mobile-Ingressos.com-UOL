package com.app.ingressocom.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.app.ingressocom.Model.Movie
import com.app.ingressocom.Model.RepositoriesMovies
import com.app.ingressocom.Model.RetroFitMovies
import com.app.ingressocom.ViewModel.MoviesViewModel
import com.app.ingressocom.ui.theme.BackgroundButtonClickable
import com.app.ingressocom.ui.theme.BackgroundMainScreen
import com.app.ingressocom.ui.theme.Buttons
import com.app.ingressocom.ui.theme.IngressocomTheme
import com.app.ingressocom.ui.theme.Letters
import com.app.ingressocom.ui.theme.LettersClickable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MainActivity : ComponentActivity() {
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false) // Para imersão

        moviesViewModel = MoviesViewModel(RepositoriesMovies(RetroFitMovies.getInstance()))
        setContent {
            IngressocomTheme {
                SetupNavController(moviesViewModel)
            }
        }
    }
}
@Composable
fun SetupNavController(moviesViewModel: MoviesViewModel) {
    val navController = rememberNavController()
    val movies by  moviesViewModel.moviesList.observeAsState(emptyList())
    NavHost(navController = navController, startDestination = "main") {
      composable("main") { MainComposableNavBottom(moviesViewModel, navController)  }
        composable("movieInformation?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id")
            for (movie in movies){
                MovieInformation(id, movie, navController)

            }
        }
}
}


@Composable
fun MainComposableNavBottom(moviesViewModel: MoviesViewModel, navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Principal", "Pesquisar")

    val homeIcon = Icons.Outlined.Home
    val listIcon = Icons.Outlined.Search

    Scaffold(
        bottomBar = {
            Column {
                // Linha acima do NavigationBar
                Divider(
                    color = BackgroundButtonClickable,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Box {
                    NavigationBar(
                        containerColor = BackgroundButtonClickable,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        tabs.forEachIndexed { index, title ->
                            NavigationBarItem(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                icon = {
                                    when (index) {
                                        0 -> Icon(homeIcon, contentDescription = title)
                                        1 -> Icon(listIcon, contentDescription = title)
                                    }
                                },
                                label = { Text(title) },
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    selectedIconColor = LettersClickable,
                                    selectedTextColor = LettersClickable,
                                    indicatorColor = BackgroundButtonClickable
                                )
                            )
                        }
                    }
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> MainScreen(moviesViewModel, navController)
                    1 -> SearchCompose(moviesViewModel, navController)
                }
            }
        }
    )
}

@Composable
fun MainScreen(moviesViewModel: MoviesViewModel, navController: NavController) {
    var showColorOnDisplay by remember { mutableStateOf(true) }
    var showColorShortly by remember { mutableStateOf(false) }
    val movies by moviesViewModel.moviesList.observeAsState(emptyList())
    val isLoading by moviesViewModel.isLoading.observeAsState(true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMainScreen)
            .padding(horizontal = 16.dp)
    ) {
        if (isLoading) {
            LoadingScreen()

        } else {
            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .width(326.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Buttons)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .padding(5.dp),
                    onClick = {
                        showColorOnDisplay = true
                        showColorShortly = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!showColorOnDisplay) Color.Transparent else BackgroundButtonClickable,
                        contentColor = if (!showColorOnDisplay) Letters else LettersClickable
                    )
                ) {
                    Text("Últimas Estreias", fontSize = 18.sp)
                }

                Button(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .padding(3.dp),
                    onClick = {
                        showColorShortly = true
                        showColorOnDisplay = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!showColorShortly) Color.Transparent else BackgroundButtonClickable,
                        contentColor = if (!showColorShortly) Letters else LettersClickable
                    )
                ) {
                    Text("Em Breve", fontSize = 18.sp)
                }

                Icon(Icons.Filled.Search, "Buscar", modifier = Modifier.background(Color.White))
            }

            if (showColorOnDisplay) {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(movies.size) { index ->
                        MoviesComposableOnDisplay(movie = movies[index], navController)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(movies.size) { index ->
                        MoviesComposableShortly(movie = movies[index], navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesComposableOnDisplay(movie: Movie, navController: NavController) {
    val premiereDate = movie.premiereDate // Acessando a data local

    if (premiereDate?.localDate == null) {
        if (movie.images.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(230.dp)
                        .width(170.dp)
                        .padding(bottom = 7.dp)
                        .clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                ) {
                    AsyncImage(
                        model = movie.images[0].url,  // Carrega a imagem real
                        contentDescription = "Image of ${movie.title}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(movie.title, color = Color.White, modifier = Modifier.padding(horizontal = 7.dp))
            }
        } else {
            Text(movie.title)
        }
    }
}
@Composable
fun MoviesComposableShortly(movie: Movie, navController: NavController) {
    val premiereDate = movie.premiereDate

    val isLocalDateAfterNow = try {
        val zonedDateTime = ZonedDateTime.now()
        val localDateTime = ZonedDateTime.parse(premiereDate?.localDate ?: "", DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        localDateTime.isAfter(zonedDateTime)
    } catch (e: DateTimeParseException) {
        false
    }

    if (isLocalDateAfterNow) {
        Column(
            modifier = Modifier.padding(top = 50.dp)
        ) {
            // Verifica se o filme tem poster, caso tenha acontece isso
            if (movie.images.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .height(230.dp)
                        .width(170.dp)
                        .padding(bottom = 7.dp)
                        .clickable {
                            navController.navigate("movieInformation?id=${movie.id}")

                        }
                ) {
                    // Verifica se ele está na pré venda
                    if(movie.inPreSale){
                        val goldGradient = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFFD700),
                                Color(0xFFFFC700),
                                Color(0xFFFFA500)
                            )
                        )

                        AsyncImage(
                            model = movie.images[0].url,
                            contentDescription = "Image of ${movie.title}",
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    BorderStroke(3.dp, goldGradient),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 5.dp)
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
                    }
                    // Caso não esteja na pré venda.
                    else{
                        AsyncImage(
                            model = movie.images[0].url,
                            contentDescription = "Image of ${movie.title}",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 5.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop)
                    }


                    Text(
                        text = "${movie.premiereDate?.dayAndMonth ?: ""}/${movie.premiereDate?.year}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(3.dp)
                            .width(170.dp)
                            .padding(bottom = 5.dp)
                            .background(BackgroundMainScreen)
                            .padding(3.dp)
                        ,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
            // Caso o filme não tenha um poster.
            else {
                Box(
                    modifier = Modifier
                        .height(220.dp)
                        .width(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                        .clickable {
                            navController.navigate("movieInformation?id=${movie.id}")
                        }
                ) {
                    Text(
                        text = "Poster Indisponível",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center).padding(5.dp)
                    )
                    Text(
                        text = "${movie.premiereDate?.dayAndMonth ?: ""}/${movie.premiereDate?.year}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .width(180.dp)
                            .padding(bottom = 5.dp)
                            .background(BackgroundMainScreen)
                            .padding(3.dp)

                        ,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,

                        fontSize = 17.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
            Text(
                text = movie.title,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = BackgroundMainScreen,
            trackColor = LettersClickable,
        )
    }
}
