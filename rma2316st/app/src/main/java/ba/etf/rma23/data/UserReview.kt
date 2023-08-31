package ba.etf.rma23.data

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
): UserImpression()
