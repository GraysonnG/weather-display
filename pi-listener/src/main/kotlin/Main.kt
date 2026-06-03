import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun notify(title: String, body: String) {
    ProcessBuilder("notify-send", "--icon=network-group", "--urgency=normal", title, body)
        .inheritIO()
        .start()
        .waitFor()
}

fun main() {
    embeddedServer(Netty, port = 9876) {
        routing {
            post("/online") {
                val time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
                val ip = call.request.local.remoteHost
                notify("\uD83D\uDFE2 Raspberry Pi Online", "[$time] Ready at $ip")
                call.respondText("ok")
            }
        }
    }.start(wait = true)
}