package com.fasterxml.jackson.module.kotlin.test

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertTrue

class Github114 {
    data class Github114TestObj(val bar: String, val baz: String) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun createFromJson(bar: String = "defaultBar", baz: String = "defaultBaz"): Github114TestObj =
                    Github114TestObj(bar, baz)
        }
    }

    @Test
    fun shouldUseDefaultValuesFromJsonCreator() {
        val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)

        val fooWithStaticCreator = mapper.readValue("""{"baz": "bazValue"}""", Github114TestObj::class.java)
        assertTrue(fooWithStaticCreator.baz == "bazValue")
        assertTrue(fooWithStaticCreator.bar == "defaultBar")
    }
}
