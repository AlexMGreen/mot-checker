package io.agapps.feature.camerasearch

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.MutableState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import timber.log.Timber

class NumberPlateImageAnalyzer(
    private val textRecognizer: TextRecognizer,
    private val detectedText: MutableState<String>
) : ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            textRecognizer.process(image)
                .addOnSuccessListener { detected ->
                    Timber.d("Detected text: ${detected.text}")
                    detectedText.value = detected.text
                    imageProxy.close()
                }
                .addOnFailureListener { error ->
                    Timber.e(error)
                    imageProxy.close()
                }
        }
    }
}
