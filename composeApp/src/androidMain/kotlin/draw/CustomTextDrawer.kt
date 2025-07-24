package draw

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas


actual fun DrawScope.drawCustomText(
    text: String,
    x: Float,
    y: Float
) {
    drawContext.canvas.nativeCanvas.drawText(
        text,
        x,
        y,
        Paint().apply {
            textSize = 72f
            color = Color.argb(255, 200, 255, 255)
            setShadowLayer(20f, 0f, 0f, Color.CYAN)
            textAlign = Paint.Align.CENTER
        }
    )
}