package com.link184.products.core.serialization

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = BigDecimal::class)
class BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)

    @InternalSerializationApi
    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }

    override fun deserialize(input: Decoder): BigDecimal {
        return BigDecimal.parseString(input.decodeString())
    }
}