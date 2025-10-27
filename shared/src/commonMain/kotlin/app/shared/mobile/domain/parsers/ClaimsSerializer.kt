package app.shared.mobile.domain.parsers

import app.shared.mobile.domain.models.user.Claims
import kmp.core.mobile.utils.extensions.orZero
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// Custom serializer for Claims
object ClaimsSerializer : KSerializer<Claims> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Claims") {
        element<String>("unique_id")
        element<String>("security_stamp")
        element<Long>("exp")
        element<List<String>>("role")
    }

    override fun deserialize(decoder: Decoder): Claims {
        var uniqueId: String? = null
        var securityStamp: String? = null
        var tokenExpiry: Long? = null
        var roles: List<String>? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop

                0 -> uniqueId = composite.decodeStringElement(descriptor, index)
                1 -> securityStamp = composite.decodeStringElement(descriptor, index)
                2 -> tokenExpiry = composite.decodeLongElement(descriptor, index)

                3 -> roles = composite.decodeSerializableElement(
                    descriptor,
                    index,
                    ListSerializer(String.serializer())
                )

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)

        return Claims().apply {
            this.uniqueId = uniqueId
            this.securityStamp = securityStamp
            this.expStamp = tokenExpiry
            this.roles = roles
        }
    }

    override fun serialize(encoder: Encoder, value: Claims) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.uniqueId.orEmpty())
        composite.encodeStringElement(descriptor, 1, value.securityStamp.orEmpty())
        composite.encodeLongElement(descriptor, 2, value.expStamp.orZero())
        composite.encodeSerializableElement(
            descriptor,
            3,
            ListSerializer(String.serializer()),
            value.roles ?: emptyList()
        )
        composite.endStructure(descriptor)
    }
}