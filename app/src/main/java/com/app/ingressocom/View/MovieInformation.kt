package com.app.ingressocom.View

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.ingressocom.Model.Movie
import com.app.ingressocom.R
import com.app.ingressocom.ui.theme.BackgroundButtonClickable
import com.app.ingressocom.ui.theme.BackgroundMainScreen
import com.app.ingressocom.ui.theme.LettersClickable

@Composable
fun MovieInformation(id: String?, movie: Movie, navController: NavController) {
    val context = LocalContext.current
    if (id == movie.id) {
        if (movie.images.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundMainScreen)
                    .verticalScroll(rememberScrollState())
            ) {
                // Botão para voltar para a tela anterior
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.Start)
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                }
                // Verificando se o filme está na Pré-venda
                if(movie.inPreSale){
                    val goldGradient = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFD700),
                            Color(0xFFFFC700),
                            Color(0xFFFFA500)
                        )
                    )
                    // Verifica se o filme tem trailer e está na pré venda.
                    if(movie.trailers.isNotEmpty()){
                        Box(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .align(Alignment.CenterHorizontally)
                                .border(BorderStroke(3.dp, goldGradient))

                        ) {
                            AsyncImage(
                                model = movie.images[0].url,
                                contentDescription = "Movie Image",
                            )

                            Icon(
                                Icons.Outlined.PlayArrow,
                                contentDescription = "Start",
                                modifier = Modifier
                                    .align(Alignment.Center) // Alinha o ícone ao centro do Box
                                    .size(48.dp) // Tamanho do ícone (pode ser ajustado conforme necessário)
                                    .background(Color.White.copy(alpha = 0.7f), shape = CircleShape) // Fundo opcional
                                    .padding(12.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.trailers[0].url))
                                        context.startActivity(intent)
                                    }
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(goldGradient)
                                .align(Alignment.CenterHorizontally)
                                .width(245.dp)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Pré-venda",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.align(Alignment.Center)
                            )

                        }
                        // Se não tiver trailer mas for pré venda, solta isso em baixo.
                    }else{
                        Box(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .align(Alignment.CenterHorizontally)
                                .border(BorderStroke(3.dp, goldGradient))

                        ) {
                            AsyncImage(
                                model = movie.images[0].url,
                                contentDescription = "Movie Image",
                            )

                        }

                        Box(
                            modifier = Modifier
                                .background(goldGradient)
                                .align(Alignment.CenterHorizontally)
                                .width(245.dp)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Pré-venda",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.align(Alignment.Center)
                            )

                        }
                    }

                }
                // Caso o filme não seja Pré-venda
                else{
                    if(movie.trailers.isNotEmpty()){

                        Box(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(300.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            AsyncImage(
                                model = movie.images[0].url,
                                contentDescription = "Movie Image",
                                modifier = Modifier.fillMaxSize()
                            )

                            Icon(
                                Icons.Outlined.PlayArrow,
                                contentDescription = "Start",
                                modifier = Modifier
                                    .align(Alignment.Center) // Alinha o ícone ao centro do Box
                                    .size(48.dp) // Tamanho do ícone (pode ser ajustado conforme necessário)
                                    .background(Color.White.copy(alpha = 0.7f), shape = CircleShape) // Fundo opcional
                                    .padding(12.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.trailers[0].url))
                                        context.startActivity(intent)
                                    }
                            )
                        }

                    } // Caso o filme não esteja na pré venda e não tenha trailer.
                    else{
                        AsyncImage(
                            model = movie.images[0].url,
                            contentDescription = "Movie Image",
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(300.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                }
                // Aqui começa a tela de detalhes do filme.
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .clip(RoundedCornerShape(topStart = 76.dp, topEnd = 76.dp))
                        .background(BackgroundButtonClickable)
                        .fillMaxSize()
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
                        color = LettersClickable,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // Caso o filme não tenha lançado ainda.
                    if(movie.premiereDate != null){
                        Text(
                            text = "Pré Estreia ${movie.premiereDate.dayAndMonth}/${movie.premiereDate.year}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(3.dp)
                                .width(170.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 8.dp)
                                .padding(3.dp)
                            ,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,

                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                    // Verificando a Classificação indicativa.
                    val contentRatingImage = when (movie.contentRating) {
                        "10 anos" -> R.drawable.ten
                        "12 anos" -> R.drawable.twelve
                        "16 anos" -> R.drawable.sixteen
                        "18 anos" -> R.drawable.eighteen
                        else -> null
                    }

                    contentRatingImage?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Content Rating",
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .size(50.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    } ?: Text(
                        text = "Verifique a classificação",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    // Gêneros do filme, verificando se tem mais de 1.
                        if(movie.genres.size > 1){
                            Text(
                                text = "Gênero: ${movie.genres[0]} e ${movie.genres[1]} ",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 45.dp)
                                    .padding(top = 45.dp)
                            )
                        }else {
                            Text(
                                text = "Gênero: ${movie.genres[0]}",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(horizontal = 45.dp)
                                    .padding(top = 45.dp)
                            )
                        }

                    Text(
                        text = "Diretor: ${movie.director}",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 45.dp, vertical = 20.dp)
                    )
                    Text(
                        text = "Atores: ${movie.cast}",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 45.dp)
                    )
                    Text(
                        text = "Sinopse: ${movie.synopsis}",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 45.dp)
                            .padding(top = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
            //Acaba aqui os detalhes do filme
        }else{
            // Esse composable traz o que acontece quando o filme não tem imagem.
           MovieImageEmpty(movie, navController)
        }
    }
}

@Composable
fun MovieImageEmpty(movie: Movie, navController: NavController){
    Column (modifier = Modifier.fillMaxSize().background(BackgroundMainScreen).verticalScroll(
        rememberScrollState()
    )){
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.Start)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
        }
        Box(
            modifier = Modifier
                .padding(top = 55.dp)
                .padding(bottom = 60.dp)
                .height(300.dp)
                .width(220.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        ) {
            Text(
                text = "Poster Indisponível",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center).padding(5.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 0.dp)
                .clip(RoundedCornerShape(topStart = 76.dp, topEnd = 76.dp))
                .background(BackgroundButtonClickable)
                .fillMaxSize()
        ) {
            Text(
                text = movie.title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 40.dp, start = 30.dp, end = 30.dp),
                color = LettersClickable,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            if (movie.premiereDate != null) {
                Text(
                    text = "Pré Estreia ${movie.premiereDate.dayAndMonth}/${movie.premiereDate.year}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(3.dp)
                        .width(170.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                        .padding(3.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,

                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                )
            }
            val contentRatingImage = when (movie.contentRating) {
                "10 anos" -> R.drawable.ten
                "12 anos" -> R.drawable.twelve
                "16 anos" -> R.drawable.sixteen
                "18 anos" -> R.drawable.eighteen
                else -> null
            }

            contentRatingImage?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Content Rating",
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } ?: Text(
                text = "Verifique a classificação",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                color = Color.White
            )

            // Gêneros do filme
            for (genre in movie.genres) {
                Text(
                    text = "Gênero: $genre",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 45.dp)
                        .padding(top = 45.dp)
                )
            }
            Text(
                text = "Diretor: ${movie.director}",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 45.dp, vertical = 20.dp)
            )
            Text(
                text = "Atores: ${movie.cast}",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 45.dp)
            )
            Text(
                text = "Sinopse: ${movie.synopsis}",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}