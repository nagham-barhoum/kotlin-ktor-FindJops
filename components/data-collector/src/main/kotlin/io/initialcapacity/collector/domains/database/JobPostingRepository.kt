package io.initialcapacity.collector.domains.database

import io.initialcapacity.collector.domains.models.JobPosting
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class JobPostingRepository(private val db: Database) {

    init {
        transaction(db) {
            // إنشاء الجدول إذا لم يكن موجود
            org.jetbrains.exposed.sql.SchemaUtils.create(JobPostingsTable)
        }
    }

    fun addJob(job: JobPosting) {
        transaction(db) {
            JobPostingsTable.insert { row ->
                  row[title] = job.title 
                  row[company] = job.company 
                  row[location] = job.location 
                  row[postedAt] = job.postedAt 
                  row[url] = job.url 
                  row[salary] = job.salary ?: ""       // إذا salary nullable
                  row[jobSource] = job.jobSource ?: "Unknown"
                  row[scrapedAt] = LocalDateTime.now()
            }
        }
    }

    fun getAllJobs(): List<JobPosting> {
        return transaction(db) {
            JobPostingsTable.selectAll().map { row ->
                JobPosting(
                    title = row[JobPostingsTable.title],
                    company = row[JobPostingsTable.company],
                    location = row[JobPostingsTable.location],
                    salary = row[JobPostingsTable.salary]?.ifEmpty { null },
                    url = row[JobPostingsTable.url],
                    postedAt = row[JobPostingsTable.postedAt],
                    jobSource = row[JobPostingsTable.jobSource]?.ifEmpty { null },
                    scrapedAt = row[JobPostingsTable.scrapedAt]
                )
            }
        }
    }
}
