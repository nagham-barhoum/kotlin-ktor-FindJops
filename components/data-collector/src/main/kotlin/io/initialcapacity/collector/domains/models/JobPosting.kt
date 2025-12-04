package io.initialcapacity.collector.domains.models

import java.time.LocalDateTime

data class JobPosting(
    val title: String,
    val company: String,
    val location: String,
    val salary: String? = null,        // nullable
    val url: String,
    val postedAt: LocalDateTime,
    val jobSource: String? = null,     // nullable, من وين جاي الإعلان
    val scrapedAt: LocalDateTime = LocalDateTime.now()  // وقت جمع البيانات
)
