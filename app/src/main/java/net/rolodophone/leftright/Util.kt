package net.rolodophone.leftright

fun centerOfLane(lane: Int): Float {
    if (lane >= road.numLanes) throw IllegalArgumentException("lane index must be less than road.numLanes")

    return (2f * width * (lane + 1) - width) / (2f * road.numLanes)
}