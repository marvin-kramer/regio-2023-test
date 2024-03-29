package ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import service.GameService

@Composable
fun UserList() {
    var exceptionMessage by remember { mutableStateOf<String?>(null) }

    Column {
        Column(
            modifier = Modifier.border(BorderStroke(1.dp, Color.Black)).padding(8.dp)
        ) {
            LazyColumn {
                items(GameService.PlayerRepository.getAll()) { player ->
                    Row(
                        modifier = Modifier.border(BorderStroke(1.dp, Color.Black))
                    ) {
                        Box(modifier = Modifier.size(40.dp).background(player.color))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(player.name)
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(
                            onClick = { GameService.PlayerRepository.remove(player.id) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Button"
                            )
                        }
                    }
                }
            }
        }

        if (GameService.gameStarted.not()) {
            AddInput(
                placeHolder = "Neuer User",
                onAdd = {
                    try {
                        GameService.PlayerRepository.new(it)
                        exceptionMessage = null
                    } catch (runtimeException: RuntimeException) {
                        exceptionMessage = runtimeException.message
                    }
                }
            )
            exceptionMessage?.let { Text(it, color = MaterialTheme.colors.error) }
        }
    }
}