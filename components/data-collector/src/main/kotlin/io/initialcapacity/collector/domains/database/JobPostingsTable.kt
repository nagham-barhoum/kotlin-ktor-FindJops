package io.initialcapacity.collector.domains.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object JobPostingsTable : Table("job_postings") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)  // تحديد المفتاح الأساسي

    val title = varchar("title", 255)
    val company = varchar("company", 255)
    val location = varchar("location", 255)
    val salary = varchar("salary", 255).nullable()  // ممكن يكون فارغ
    val url = varchar("url", 512)
    val postedAt = datetime("posted_at")
    val jobSource = varchar("job_source", 255).nullable()  // بديل عن source لتجنب التعارض
    val scrapedAt = datetime("scraped_at").clientDefault { LocalDateTime.now() }
}
