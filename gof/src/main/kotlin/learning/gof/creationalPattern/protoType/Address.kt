package learning.gof.creationalPattern.protoType

import learning.gof.creationalPattern.ProtoType

data class Address(val city: String, val country: String) : ProtoType<Address> {
    override fun clone(): Address = this.copy()
}