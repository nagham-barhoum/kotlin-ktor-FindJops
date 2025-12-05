package io.initialcapacity.analyzer

import io.initialcapacity.collector.domains.database.JobPostingRepository

class AnalyzerRunner(private val repository: JobPostingRepository) {

    private val analyzer = DataAnalyzer()

    fun analyzeAllJobs(): List<AnalyzedJob> {
        val jobs = repository.getAllJobs()
        return jobs.map { analyzer.analyze(it) }
    }
}
