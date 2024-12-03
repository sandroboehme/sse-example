package com.example

import io.ktor.client.plugins.sse.*
import io.ktor.server.testing.*
import kotlinx.coroutines.flow.collectIndexed
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testLimitedEvents() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(SSE)
            }

            client.sse("/limitedEvents") {
                incoming.collectIndexed { i, event ->
                    assertEquals("this is SSE #$i", event.data)
                }
            }
        }
    }
    @Test
    fun testUnlimitedEvents() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(SSE)
            }

            client.sse("/unlimitedEvents") {
                println("This is never called")
                incoming.collectIndexed { i, event ->
                    assertEquals("this is SSE #$i", event.data)
                }
            }
            println("The request never comes back. That's why this line is also never reached.")
        }
    }
}
