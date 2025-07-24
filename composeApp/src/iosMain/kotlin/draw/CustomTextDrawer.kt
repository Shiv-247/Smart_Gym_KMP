package draw

import androidx.compose.ui.graphics.drawscope.DrawScope

actual fun DrawScope.drawCustomText(
    text: String,
    x: Float,
    y: Float
) {
    // iOS-specific implementation TODO (if needed)
    // For now, we just skip it
}