package com.retr0.projectretr0.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Premium 3D Tilt effect.
 * Responsive to touch/pointer input to simulate a premium console-style depth.
 */
fun Modifier.tiltEffect(
    maxRotation: Float = 10f,
): Modifier = composed {
    var rotationX by remember { mutableFloatStateOf(0f) }
    var rotationY by remember { mutableFloatStateOf(0f) }
    
    val animatedRotationX by animateFloatAsState(
        targetValue = rotationX,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 150f),
        label = "tiltX",
    )
    val animatedRotationY by animateFloatAsState(
        targetValue = rotationY,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 150f),
        label = "tiltY",
    )

    this.then(
        Modifier
            .graphicsLayer {
                this.rotationX = animatedRotationX
                this.rotationY = animatedRotationY
                this.cameraDistance = 12f * density
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val position = event.changes.first().position
                        
                        // Calculate rotation based on center of the component
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f
                        
                        rotationY = ((position.x - centerX) / centerX) * maxRotation
                        rotationX = -((position.y - centerY) / centerY) * maxRotation
                        
                        // Reset if pointer leaves or event is canceled
                        if (event.changes.any { it.pressed.not() }) {
                            rotationX = 0f
                            rotationY = 0f
                        }
                    }
                }
            },
    )
}
