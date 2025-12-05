package io.initialcapacity.web

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8888, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(FreeMarker)

    val client = HttpClient(CIO)

    routing {
        get("/") {
            // نطلب البيانات من Analyzer Server
            val jobs: List<Map<String, Any>> = client.get("http://localhost:8887/it-jobs").body()
            call.respond(FreeMarkerContent("jobs.ftl", mapOf("jobs" to jobs)))
        }
    }
}
