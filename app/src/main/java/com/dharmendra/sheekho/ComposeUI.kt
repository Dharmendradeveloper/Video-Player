package com.dharmendra.sheekho

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.dharmendra.domain.jikandata.JikanData

@Composable
fun CardView(
    modifier: Modifier,
    animList: State<List<JikanData>>,
    context: Context
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds spacing between items
    ) {
        items(animList.value) { anim ->
            RecyclerViewItem(it = anim, context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclerViewItem(it: JikanData, context: Context) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        onClick = {

        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .clickable {
                    val intent = Intent(context, PlayVideodetailsActivity::class.java)
                    intent.putExtra("anim_id", it.anim_id)
                    context.startActivity(intent)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = it.imgUrl,
                    error = painterResource(id = R.drawable.ic_launcher_background)
                ),
                contentDescription = "Loaded Image",
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Title: ${it.title}",
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                )

                Text(
                    text = "No.Of.Episode: ${it.noOfEpisode}",
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Text(
                    text = "Rating: ${it.rating}",
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                )

            }
        }

    }

}

@Composable
fun ImageWithProgressBar(imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            // Show CircularProgressIndicator when the image is loading
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
            )
        }

        is AsyncImagePainter.State.Error -> {
            // Handle error state, like showing a placeholder or error message
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Error Image",
                modifier = Modifier.size(150.dp)
            )
        }

        is AsyncImagePainter.State.Success -> {
            // Show the loaded image
            Image(
                painter = painter,
                contentDescription = "Loaded Image",
                modifier = Modifier.size(150.dp)
            )
        }

        else -> {
            // Handle other states if needed
        }
    }
}
