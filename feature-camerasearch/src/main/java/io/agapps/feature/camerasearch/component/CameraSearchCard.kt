package io.agapps.feature.camerasearch.component

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import io.agapps.core.ui.component.IconLabel
import io.agapps.feature.camerasearch.NumberPlateImageAnalyzer
import io.agapps.feature.camerasearch.R
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.White10
import io.agapps.motchecker.ui.theme.White50
import java.util.concurrent.Executors

private const val ImageGradientEnd = 600f
private const val ExpandedCardHeightRatio = 0.5f
private const val CollapsedCardHeightRatio = 0.25f

@Composable
fun CameraSearchCard(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
) {
    val expandedState = remember { MutableTransitionState(expanded) }
    val transition = updateTransition(expandedState, label = "")

    val fadeOutOnExpandAlpha by transition.animateFloat({ tween(durationMillis = 500) }, label = "") { isExpanded -> if (isExpanded) 0f else 1f }
    val fadeInOnExpandAlpha by transition.animateFloat({ tween(durationMillis = 500) }, label = "") { isExpanded -> if (isExpanded) 1f else 0f }
    val cardHeightRatio by transition.animateFloat(
        { tween(durationMillis = 500) },
        label = ""
    ) { isExpanded -> if (isExpanded) ExpandedCardHeightRatio else CollapsedCardHeightRatio }

    Card(
        elevation = 8.dp,
        modifier = modifier
            .padding(16.dp)
            .clickable {
                expandedState.targetState = !expandedState.currentState
            }
            .fillMaxWidth()
            .fillMaxHeight(cardHeightRatio)
            .border(width = 1.dp, color = White50, shape = Shapes.medium)

    ) {
        CollapsedCardContents(fadeOutOnExpandAlpha)
        ExpandedCardContents(alpha = fadeInOnExpandAlpha, expandedState = expandedState)
    }
}

@Composable
private fun CollapsedCardContents(
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.alpha(alpha)) {
        Image(
            painterResource(id = R.drawable.camera_search_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), 0f, ImageGradientEnd))
        )

        IconLabel(
            label = stringResource(id = R.string.search_using_camera),
            elevation = 0.dp,
            backgroundColor = White10,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "")
        }
    }
}

@Composable
private fun ExpandedCardContents(
    alpha: Float,
    modifier: Modifier = Modifier,
    expandedState: MutableTransitionState<Boolean>
) {
    Box(modifier = modifier.alpha(alpha)) {
        if (expandedState.currentState) {
            CameraPreview()
        }

        IconLabel(
            label = stringResource(id = io.agapps.core.ui.R.string.close),
            elevation = 0.dp,
            backgroundColor = White10,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Filled.Close, stringResource(id = io.agapps.core.ui.R.string.close_search))
        }
    }

}

@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    val lifecycleOwner = LocalLifecycleOwner.current

    val detectedText = remember { mutableStateOf("") }
    val recognizer by remember { mutableStateOf(TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)) }
    val analyzer = NumberPlateImageAnalyzer(recognizer, detectedText)

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = cameraProviderFuture.get()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var preview by remember { mutableStateOf<Preview?>(null) }

    Box {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                cameraProviderFuture.addListener({
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .apply {
                            setAnalyzer(cameraExecutor, analyzer)
                        }

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageAnalysis, preview)
                }, executor)

                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                previewView
            }
        )

        Text(text = detectedText.value, modifier = Modifier.align(Alignment.Center))
    }
}
