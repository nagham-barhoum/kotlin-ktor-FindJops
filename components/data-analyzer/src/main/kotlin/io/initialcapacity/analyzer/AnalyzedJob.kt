package components.data_analyzer

data class AnalyzedJob(
    val id: Int,
    val title: String,
    val company: String,
    val tags: List<String>
)
