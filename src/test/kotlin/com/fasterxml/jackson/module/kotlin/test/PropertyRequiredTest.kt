package com.fasterxml.jackson.module.kotlin.test

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyRequiredTest {

    private val normalCasedMapper = jacksonObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, false)

    // ==================

    private data class SimpleDataClass(
            val nonNullableField: Int,
            val nullableField: Int?
    )

    @Test fun inferIsRequiredFlagBasingOnPropertyNullity() {
        assertTrue { introspectSerialization(SimpleDataClass::class.java).isRequired("nonNullableField") }
        assertFalse { introspectSerialization(SimpleDataClass::class.java).isRequired("nullableField") }
    }

    private fun introspectSerialization(type: Class<*>): BeanDescription =
            normalCasedMapper.serializationConfig.introspect(normalCasedMapper.serializationConfig.constructType(type))



    private fun BeanDescription.isRequired(propertyName: String): Boolean =
            this.findProperties().find { it.name == propertyName }!!.isRequired
}

