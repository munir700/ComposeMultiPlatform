package learning.gof.creationalPattern.protoType

import learning.gof.creationalPattern.ProtoType

data class Person(val name: String, val age: Int, val address: Address) : ProtoType<Person> {
    override fun clone(): Person {
        return this.copy(address = address.clone(), name = name)
    }
}