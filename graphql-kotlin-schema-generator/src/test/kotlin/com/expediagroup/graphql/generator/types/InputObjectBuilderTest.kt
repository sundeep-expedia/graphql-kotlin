/*
 * Copyright 2019 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.graphql.generator.types

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.test.utils.SimpleDirective
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class InputObjectBuilderTest : TypeTestHelper() {

    private lateinit var builder: InputObjectBuilder

    override fun beforeTest() {
        builder = InputObjectBuilder(generator)
    }

    @Suppress("Detekt.UnusedPrivateClass")
    @GraphQLDescription("The truth")
    @SimpleDirective
    private class InputClass {
        @SimpleDirective
        val myField: String = "car"
    }

    @Suppress("Detekt.UnusedPrivateClass")
    @GraphQLName("InputClassRenamed")
    private class InputClassCustomName {
        val myField: String = "car"
    }

    @Test
    fun `Test naming`() {
        val result = builder.inputObjectType(InputClass::class)
        assertEquals("InputClassInput", result.name)
    }

    @Test
    fun `Test custom naming`() {
        val result = builder.inputObjectType(InputClassCustomName::class)
        assertEquals("InputClassRenamedInput", result.name)
    }

    @Test
    fun `Test description`() {
        val result = builder.inputObjectType(InputClass::class)
        assertEquals("The truth", result.description)
    }

    @Test
    fun `directives should be on input objects`() {
        val result = builder.inputObjectType(InputClass::class)
        assertEquals(1, result.directives.size)
        assertEquals("simpleDirective", result.directives.first().name)
    }

    @Test
    fun `directives should be on input object fields`() {
        val result = builder.inputObjectType(InputClass::class)
        assertEquals(1, result.fields.first().directives.size)
        assertEquals("simpleDirective", result.fields.first().directives.first().name)
    }
}
