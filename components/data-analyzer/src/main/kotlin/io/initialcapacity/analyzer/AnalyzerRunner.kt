package components.data_analyzer

import db.DatabaseManager

class AnalyzerRunner {

   
    private val analyzer = DataAnalyzer()

    fun analyzeAllJobs(): List<AnalyzedJob> {
        val allJobs = DatabaseManager.getAllJobs()
        return allJobs.mapNotNull { job ->
            analyzer.analyze(job) // إذا null → مالو اختصاص IT → يتجاهل
        }
    }
}
