package components.data_analyzer

import models.JobPosting

class DataAnalyzer {

    fun analyze(job: JobPosting): AnalyzedJob? {
        val title = job.position?.lowercase() ?: ""

        val tags = mutableListOf<String>()

        if ("backend" in title) tags.add("backend")
        if ("laravel" in title) tags.add("laravel")
        if ("devops" in title) tags.add("devops")
        if ("engineer" in title) tags.add("engineer")

        if (tags.isEmpty()) return null  // مالو اختصاص IT → منطنشو

        return AnalyzedJob(
            id = job.id,
            title = job.position ?: "",
            company = job.company ?: "",
            tags = tags
        )
    }
}
