package net.rolodophone.leftright

class GameObjectArray<E: GameObject> : GameObject {

    private val array = mutableListOf<E>()


    val size: Int
        get() = array.size


    override fun update() { for (obj in array) obj.update() }
    override fun draw() { for (obj in array) obj.draw() }


    fun add(element: E): Boolean = array.add(element)

}