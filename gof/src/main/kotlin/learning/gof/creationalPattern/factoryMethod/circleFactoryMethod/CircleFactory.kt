package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

import learning.gof.creationalPattern.factoryMethod.circleFactoryMethod.ShapeFactory

// Concrete Factories (decide *which prototype* to clone)
class CircleFactory(private val prototype: Circle) : ShapeFactory() {
    override fun createShape(): Shape = prototype.clone()
}