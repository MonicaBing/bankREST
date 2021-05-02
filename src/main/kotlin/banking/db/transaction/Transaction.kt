package banking.db.transaction

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// payment -
object PayeeTransactionTable : IntIdTable() {
    val accId = uuid("acc_id")
    val payment = double("payment")
    val time = varchar("time", 20)
    val key = uuid("key") // generate a key to link them tgt
}

class PayeeTransaction(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PayeeTransaction>(PayeeTransactionTable)

    var accId by PayeeTransactionTable.accId
    var payment by PayeeTransactionTable.payment
    var time by PayeeTransactionTable.time
    var key by PayeeTransactionTable.key
}

// receive +
object ReceiveTransactionTable : IntIdTable() {
    val accId = uuid("acc_id")
    val payment = double("payment")
    val time = varchar("time", 20)
    val key = uuid("key")
}


class ReceiveTransaction(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ReceiveTransaction>(ReceiveTransactionTable)

    var accId by ReceiveTransactionTable.accId
    var payment by ReceiveTransactionTable.payment
    var time by ReceiveTransactionTable.time
    var key by ReceiveTransactionTable.key
}