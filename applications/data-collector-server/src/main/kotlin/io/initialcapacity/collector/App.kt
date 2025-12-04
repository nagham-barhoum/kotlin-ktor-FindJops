package com.analyzer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.freemarker.*
import components.data_analyzer.AnalyzerRunner

fun Application.module() {
    val analyzerRunner = AnalyzerRunner()

    install(Routing) {
        get("/it-jobs") {
            val results = analyzerRunner.analyzeAllJobs()
            call.respond(results) // JSON
        }

        get("/it-jobs-html") {
            val results = analyzerRunner.analyzeAllJobs()
            call.respond(FreeMarkerContent("jobs.ftl", mapOf("jobs" to results)))
        }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8887
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = { module() }).start(wait = true)
}