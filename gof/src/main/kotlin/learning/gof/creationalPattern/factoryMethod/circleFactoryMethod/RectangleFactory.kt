package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

import learning.gof.creationalPattern.factoryMethod.circleFactoryMethod.ShapeFactory

class RectangleFactory(private val prototype: Rectangle) : ShapeFactory() {
    override fun createShape(): Shape = prototype.clone()
}