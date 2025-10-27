package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

import learning.gof.creationalPattern.ProtoType

// Product interface
interface Shape : ProtoType<Shape> {
    fun draw()
}