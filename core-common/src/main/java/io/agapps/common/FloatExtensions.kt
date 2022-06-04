package io.agapps.common

// Maps a value, this, in one range of values, to the equivalent value in a new range, maintaining ratio
fun Float.mapRange(inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    return (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
}
