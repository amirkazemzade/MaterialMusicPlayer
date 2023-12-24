package me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import me.amirkazemzade.materialmusicplayer.presentation.common.materialmusicicons.MaterialMusicIcons


@Preview
@Composable
private fun VectorPreview() {
    Image(
        modifier = Modifier.background(Color.White),
        imageVector = MaterialMusicIcons.SortDescending,
        contentDescription = null
    )
}

private var sortDescending: ImageVector? = null

val MaterialMusicIcons.SortDescending: ImageVector
    get() {
        if (sortDescending != null) {
            return sortDescending!!
        }
        sortDescending = materialIcon(name = "SortDescending", autoMirror = true) {
            materialPath {
                moveTo(16f, 18f)
                horizontalLineTo(4f)
                curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
                reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
                horizontalLineToRelative(12f)
                curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
                reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
                close()
                moveTo(13f, 13f)
                horizontalLineTo(4f)
                curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
                reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
                horizontalLineToRelative(9f)
                curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
                reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
                close()
                moveTo(10f, 8f)
                horizontalLineTo(4f)
                curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
                reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
                horizontalLineToRelative(6f)
                curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
                reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
                close()
                moveTo(18f, 14f)
                curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
                verticalLineTo(5f)
                curveToRelative(0f, -0.6f, 0.4f, -1f, 1f, -1f)
                reflectiveCurveToRelative(1f, 0.4f, 1f, 1f)
                verticalLineToRelative(8f)
                curveToRelative(0f, 0.6f, -0.4f, 1f, -1f, 1f)
                close()
            }
            materialPath {
                moveTo(21f, 9f)
                curveToRelative(-0.3f, 0f, -0.5f, -0.1f, -0.7f, -0.3f)
                lineTo(18f, 6.4f)
                lineToRelative(-2.3f, 2.3f)
                curveToRelative(-0.4f, 0.4f, -1f, 0.4f, -1.4f, 0f)
                reflectiveCurveToRelative(-0.4f, -1f, 0f, -1.4f)
                lineToRelative(3f, -3f)
                curveToRelative(0.4f, -0.4f, 1f, -0.4f, 1.4f, 0f)
                lineToRelative(3f, 3f)
                curveToRelative(0.4f, 0.4f, 0.4f, 1f, 0f, 1.4f)
                curveToRelative(-0.2f, 0.2f, -0.4f, 0.3f, -0.7f, 0.3f)
                close()
            }
        }
//        sortDescending = ImageVector.Builder(
//            name = "SortDescending",
//            defaultWidth = 24.dp,
//            defaultHeight = 24.dp,
//            viewportWidth = 24f,
//            viewportHeight = 24f,
//            autoMirror = true,
//        ).apply {
//            group {
//                path(
//                    fill = SolidColor(Color(0xFF000000)),
//                    fillAlpha = 1f,
//                    stroke = null,
//                    strokeAlpha = 1f,
//                    strokeLineWidth = 1.0f,
//                    strokeLineCap = StrokeCap.Butt,
//                    strokeLineJoin = StrokeJoin.Miter,
//                    strokeLineMiter = 1.0f,
//                    pathFillType = PathFillType.NonZero
//                ) {
//                    moveTo(16f, 18f)
//                    horizontalLineTo(4f)
//                    curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
//                    reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
//                    horizontalLineToRelative(12f)
//                    curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
//                    reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
//                    close()
//                    moveTo(13f, 13f)
//                    horizontalLineTo(4f)
//                    curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
//                    reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
//                    horizontalLineToRelative(9f)
//                    curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
//                    reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
//                    close()
//                    moveTo(10f, 8f)
//                    horizontalLineTo(4f)
//                    curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
//                    reflectiveCurveToRelative(0.4f, -1f, 1f, -1f)
//                    horizontalLineToRelative(6f)
//                    curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
//                    reflectiveCurveToRelative(-0.4f, 1f, -1f, 1f)
//                    close()
//                    moveTo(18f, 14f)
//                    curveToRelative(-0.6f, 0f, -1f, -0.4f, -1f, -1f)
//                    verticalLineTo(5f)
//                    curveToRelative(0f, -0.6f, 0.4f, -1f, 1f, -1f)
//                    reflectiveCurveToRelative(1f, 0.4f, 1f, 1f)
//                    verticalLineToRelative(8f)
//                    curveToRelative(0f, 0.6f, -0.4f, 1f, -1f, 1f)
//                    close()
//                }
//                path(
//                    fill = SolidColor(Color(0xFF000000)),
//                    fillAlpha = 1f,
//                    stroke = null,
//                    strokeAlpha = 1f,
//                    strokeLineWidth = 1.0f,
//                    strokeLineCap = StrokeCap.Butt,
//                    strokeLineJoin = StrokeJoin.Miter,
//                    strokeLineMiter = 1.0f,
//                    pathFillType = PathFillType.NonZero
//                ) {
//                    moveTo(21f, 9f)
//                    curveToRelative(-0.3f, 0f, -0.5f, -0.1f, -0.7f, -0.3f)
//                    lineTo(18f, 6.4f)
//                    lineToRelative(-2.3f, 2.3f)
//                    curveToRelative(-0.4f, 0.4f, -1f, 0.4f, -1.4f, 0f)
//                    reflectiveCurveToRelative(-0.4f, -1f, 0f, -1.4f)
//                    lineToRelative(3f, -3f)
//                    curveToRelative(0.4f, -0.4f, 1f, -0.4f, 1.4f, 0f)
//                    lineToRelative(3f, 3f)
//                    curveToRelative(0.4f, 0.4f, 0.4f, 1f, 0f, 1.4f)
//                    curveToRelative(-0.2f, 0.2f, -0.4f, 0.3f, -0.7f, 0.3f)
//                    close()
//                }
//            }
//        }.build()
        return sortDescending!!
    }

