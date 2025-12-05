package io.initialcapacity.analyzer

import io.initialcapacity.collector.domains.database.JobPostingRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.freemarker.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8887, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(FreeMarker)

    // الربط مباشرة مع DB
    val db = Database.connect("jdbc:sqlite:jobs.db", "org.sqlite.JDBC")
    val repository = JobPostingRepository(db)
    val analyzerRunner = AnalyzerRunner(repository)

    routing {
        get("/it-jobs-html") {
            val results = analyzerRunner.analyzeAllJobs()
            call.respond(
                FreeMarkerContent("jobs.ftl", mapOf("jobs" to results))
            )
        }

        get("/it-jobs") {
            val results = analyzerRunner.analyzeAllJobs()
            call.respond(results)
        }
    }
}
