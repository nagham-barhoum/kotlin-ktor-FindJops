package io.initialcapacity.analyzer

data class AnalyzedJob(
    val title: String,
    val location: String,
    val company: String,
    val isBackend: Boolean,
    val isJunior: Boolean
)
