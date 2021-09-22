package banking.web.transaction
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

// result row return something we dont want, (*everything) therefore we are customerising it to this
class TransactionDto(val account: UUID, val amount: Double)

class UsersDto(
    val id: UUID,
    val name: String,
    val userName: String?
)

class AccountsDto(
    val id: UUID,
    val userId:UUID,
    val type: String,
    val balance: Double
)

