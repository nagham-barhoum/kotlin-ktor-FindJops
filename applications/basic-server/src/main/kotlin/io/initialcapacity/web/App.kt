import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.freemarker.*
import components.data_analyzer.AnalyzerRunner

fun Application.module() {

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    routing {
        get("/") {
            val client = HttpClient()
            val jobs: List<AnalyzedJob> =
                client.get("http://localhost:8887/it-jobs").body()

            call.respond(FreeMarkerContent("jobs.ftl", mapOf("jobs" to jobs)))
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8888, host = "0.0.0.0", module = { module() }).start(wait = true)
}
