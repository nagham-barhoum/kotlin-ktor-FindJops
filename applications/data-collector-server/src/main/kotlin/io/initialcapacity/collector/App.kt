package io.initialcapacity.collector

import io.initialcapacity.collector.domains.database.JobPostingRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.initialcapacity.collector.RemoteOkCollector
import org.jetbrains.exposed.sql.Database

fun main() {
    val port = 8886
    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val db = Database.connect("jdbc:sqlite:jobs.db", "org.sqlite.JDBC")
    val repository = JobPostingRepository(db)
    val collector = RemoteOkCollector(repository)

    // شغل جمع البيانات عند بدء السيرفر
    collector.collectAndStore()

    routing {
        get("/") {
            call.respondText("Data Collector Server is running!")
        }
    }
}
