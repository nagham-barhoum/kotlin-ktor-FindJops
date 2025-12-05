package io.initialcapacity.analyzer

import io.initialcapacity.collector.domains.models.JobPosting

class DataAnalyzer {

    fun analyze(job: JobPosting): AnalyzedJob {
        val lowerTitle = job.title.lowercase()

        val isBackend = "backend" in lowerTitle
        val isJunior = "junior" in lowerTitle

        return AnalyzedJob(
            title = job.title,
            location = job.location,
            company = job.company,
            isBackend = isBackend,
            isJunior = isJunior
        )
    }
}
