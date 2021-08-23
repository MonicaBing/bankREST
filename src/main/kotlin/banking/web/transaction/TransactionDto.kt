package banking.web.transaction

import java.util.*

// result row return something we dont want, (*everything) therefore we are customerising it to this
class TransactionDto(val account: UUID, val amount: Double)