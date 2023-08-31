package ba.etf.rma23.projekat.data.repositories

// Klasa koja služi za slanja zahtjeva na saveGame metodi
data class GameRequest(
    val game: RMAGame
)