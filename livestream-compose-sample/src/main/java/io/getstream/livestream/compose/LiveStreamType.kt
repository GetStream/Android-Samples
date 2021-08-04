package io.getstream.livestream.compose

enum class LiveStreamType {
    Youtube,
    Camera,
    Video
}

fun get(number: Int): LiveStreamType {
    val allValues: Array<LiveStreamType> = LiveStreamType.values()
    return allValues[number]
}
