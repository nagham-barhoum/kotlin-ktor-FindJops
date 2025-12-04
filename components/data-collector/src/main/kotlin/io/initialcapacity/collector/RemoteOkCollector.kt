package io.initialcapacity.collector

import io.initialcapacity.collector.domains.models.JobPosting
import io.initialcapacity.collector.domains.database.JobPostingRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.decodeFromString
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import java.time.LocalDateTime

class RemoteOkCollector(private val repository: JobPostingRepository) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    suspend fun fetch(): List<JobPosting> {
        val apiUrl = "https://remoteok.com/api"
        val responseText: String = client.get(apiUrl).body()
    
        val jsonArray: JsonArray = Json.parseToJsonElement(responseText).jsonArray
    
        // تجاهل أول عنصر لأنه terms of service
        val jobsJson = jsonArray.drop(1)
    
        val json = Json { ignoreUnknownKeys = true }
    
          val data: List<RemoteOkJob> = jobsJson.map { 
            json.decodeFromString<RemoteOkJob>(it.toString())
        }
    
        return data
            .filter { it.position != null }
           .filter { job ->
            val title = job.position!!.lowercase()
            val tags = job.tags?.map { it.lowercase() } ?: emptyList()
            
            // نسمح لكل الوظائف اللي فيها Backend أو Engineer
            val isRelevant = "backend" in title || "backend" in tags ||
                             "engineer" in title || "engineer" in tags
            
            isRelevant
            }
            .map { job ->
                JobPosting(
                    title = job.position ?: "",
                    company = job.company ?: "Unknown",
                    location = job.location ?: "",
                    salary = job.salary ?: "",
                    url = job.url ?: "",
                    jobSource = "RemoteOK",
                    postedAt = LocalDateTime.now()
                )
            }
    }
  // دالة لتخزين الوظائف في الـ DB
    fun collectAndStore() = runBlocking {
        val jobs = fetch()
        jobs.forEach { repository.addJob(it) }
        println("${jobs.size} jobs added to DB.")
    }
}
    // نموذج البيانات المأخوذة من API
    @Serializable
    data class RemoteOkJob(
        val position: String? = null,
        val company: String? = null,
        val location: String? = null,
        val salary: String? = null,
        val url: String? = null,
        val tags: List<String>? = null
    )

