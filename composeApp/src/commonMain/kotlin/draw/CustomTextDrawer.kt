package draw

import androidx.compose.ui.graphics.drawscope.DrawScope

expect fun DrawScope.drawCustomText(
    text: String,
    x: Float,
    y: Float
)