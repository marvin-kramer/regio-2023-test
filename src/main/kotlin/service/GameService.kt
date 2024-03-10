package service

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import utils.ExceptionCode
import java.util.UUID
import kotlin.random.Random

const val MAX_PLAYER = 10
const val MAX_CARD_B = 5
const val MAX_CARD_R = 6

class GameService {
    var gameStarted by mutableStateOf(false)

    private var players = mutableStateMapOf<UUID, User>()

    private var events = mutableStateListOf<Event>()

    var roundNumer by mutableStateOf(0)
    var cardsB by mutableStateOf(0)
    var cardsR by mutableStateOf(0)

    fun startOrReset() {
        val newUser = User(
            id = UUID.randomUUID(),
            name = "Theao",
            color = generateRandomColor()
        )
        val newUser2 = User(
            id = UUID.randomUUID(),
            name = "Theao",
            color = generateRandomColor()
        )
        events.add(Event(1, 17, 0, 0, 0, newUser, newUser2, "yes", 'R', "RRR", "RR"))
        if (gameStarted) {
            gameStarted = false
            players = mutableStateMapOf()
        } else {
            gameStarted = true
        }
    }

    fun addPlayer(name: String) {
        if (players.size == MAX_PLAYER) {
            throw RuntimeException(ExceptionCode.MAX_USER.name)
        } else if (players.values.find { it.name == name } != null) {
            throw RuntimeException(ExceptionCode.USER_EXISTS.name)
        }

        val newUser = User(
            id = UUID.randomUUID(),
            name = name,
            color = generateRandomColor()
        )
        players[newUser.id] = newUser
    }

    fun getPlayers() = players.values.toList().sortedBy { it.name }

    fun removePlayer(id: UUID) {
        players.remove(id)
    }

    fun getEvents() = events.toList()

}

// ChatGPT
fun generateRandomColor(): Color {
    val hue = Random.nextFloat() * 360 // 0-360 degrees
    val saturation = 0.5f + Random.nextFloat() * 0.5f // 50-100%
    val lightness = 0.3f + Random.nextFloat() * 0.4f // 30-70%
    val alpha = 1f // Fully opaque

    // Use the hsl method to create a color from the random values
    return Color.hsl(hue, saturation, lightness, alpha)
}