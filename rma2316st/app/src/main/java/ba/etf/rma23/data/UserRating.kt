package ba.etf.rma23.data

data class UserRating(
    override val username: String,
    override val timestamp: Long,
    val rating: Double
): UserImpression()
