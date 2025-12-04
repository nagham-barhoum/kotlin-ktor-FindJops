package io.initialcapacity.collector

import io.initialcapacity.collector.domains.database.JobPostingRepository
import org.jetbrains.exposed.sql.Database

fun main() {
    val db = Database.connect("jdbc:sqlite:jobs.db", driver = "org.sqlite.JDBC")
    val repository = JobPostingRepository(db)
    val collector = RemoteOkCollector(repository)

    collector.collectAndStore()
}