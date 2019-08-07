package net.rolodophone.leftright

fun centerOfLane(lane: Int): Float {
    if (lane >= Road.NUM_LANES) throw IllegalArgumentException("lane index must be less than Road.NUM_LANES")

    return (2f * width * (lane + 1) - width) / (2f * Road.NUM_LANES)
}