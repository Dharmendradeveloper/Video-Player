package com.dharmendra.sheekho

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.UnstableApi
import coil.compose.rememberAsyncImagePainter
import com.dharmendra.domain.jikandatadetails.JikanDatadetails
import com.dharmendra.sheekho.ui.theme.SheekhoTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.wait

@AndroidEntryPoint
class PlayVideodetailsActivity : ComponentActivity() {
    private val movieViewModel: PlayVideoDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SheekhoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val id = intent.getIntExtra("anim_id", 0)
                    println("Anim ID:$id")
                    movieViewModel.getJikanDetails(animID = id)
                    val item = movieViewModel.data.collectAsState().value

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray)
                    ) {
                        MyVideoLayout(item)
                    }

                }
            }
        }
    }
}

@Composable
fun MyVideoLayout(item: JikanDatadetails) {
    VideoViewLayout(data = item)

}

@OptIn(UnstableApi::class)
@Composable
fun VideoViewLayout(data: JikanDatadetails) {
    // Fetching the Local Context
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(2.dp, color = Color.White),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp).padding(12.dp)
        ) {

            if (data.videoUrl != null) {
                // Declaring ExoPlayer
                var lifecycle by remember {
                    mutableStateOf(Lifecycle.Event.ON_CREATE)
                }
                val context = LocalContext.current
                Toast.makeText(context, data.videoUrl, Toast.LENGTH_SHORT).show()
                //"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
                /*val mediaItem = MediaItem.fromUri("${ data.videoUrl!! }.mp4")
                val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory()).createMediaSource(mediaItem)
                val exoPlayer = remember{
                    ExoPlayer.Builder(context).build().apply {
                        //setMediaItem(mediaItem) // for .mp4 it is valid
                        setMediaSource(mediaSource)
                        playWhenReady = true
                    }
                }

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner){
                    val observer = LifecycleEventObserver{ _, event->
                        lifecycle = event

                    }
                    lifecycleOwner.lifecycle.addObserver(observer = observer)
                    onDispose {
                        exoPlayer.release()
                        lifecycleOwner.lifecycle.addObserver(observer = observer)
                    }

                }

                AndroidView(modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(18f / 9f),
                    factory = {
                    PlayerView(context).also { playerView ->
                        playerView.player = exoPlayer
                    }
                },
                    update = {
                        when (lifecycle) {
                            Lifecycle.Event.ON_RESUME -> {
                               it.onPause()
                                it.player?.pause()
                            }

                            Lifecycle.Event.ON_PAUSE -> {
                                it.onResume()
                            }

                            else -> Unit
                        }
                    })*/

                AndroidView(
                    factory = { context ->
                        val view = YouTubePlayerView(context)
                        view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(
                                    data.videoUrl!!,
                                    0f
                                ) // Load video from the beginning
                            }
                        })
                        view
                    },
                    update = { it.addFullscreenListener(object : FullscreenListener{
                        override fun onEnterFullscreen(
                            fullscreenView: View,
                            exitFullscreen: () -> Unit
                        ) {
                            Toast.makeText(context,"FullScreen is enabled",Toast.LENGTH_SHORT).show()
                        }

                        override fun onExitFullscreen() {
                            Toast.makeText(context,"FullScreen is disabled",Toast.LENGTH_SHORT).show()

                        }

                    }) } // Optional: Handle updates like fullscreen
                )

            } else {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = data.imgUrl,
                        error = painterResource(id = R.drawable.ic_launcher_background)
                    ),
                    contentDescription = "Loaded Image",
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.White)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = "Title: ${data.title}", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(color = Color.White)
                )
                Text(
                    text = "Synopsis: ${data.synopsis}", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(color = Color.White)
                )
                Text(
                    text = "MainCast: ${data.mainCast}", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(color = Color.White)
                )
                Text(
                    text = "No.Of.Episode: ${data.noOfEpisode}", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(color = Color.White)
                )
                Text(
                    text = "Rating: ${data.rating}", style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
        }
    }
}

