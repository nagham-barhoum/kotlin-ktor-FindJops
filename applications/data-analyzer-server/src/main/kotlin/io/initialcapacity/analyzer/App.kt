package io.initialcapacity.analyzer


import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import java.util.*

fun Application.module() {
    val analyzer = AnalyzerRunner()

    routing {
        get("/it-jobs") {
            val data = analyzer.analyzeAllJobs()
            call.respond(data)
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8887, host = "0.0.0.0", module = { module() }).start(wait = true)
}