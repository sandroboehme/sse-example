package com.example

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    install(SSE)

    routing {
        sse("/events") {
            repeat(6) {
                send(ServerSentEvent("this is SSE #$it"))
                delay(1000)
            }
        }
        sse("/events2") {
            var i = 1
            while(true) {
                val msg = "this is SSE #$i"
                send(ServerSentEvent(msg))
                println(msg)
                delay(1000)
                i++
            }
        }
    }
}
