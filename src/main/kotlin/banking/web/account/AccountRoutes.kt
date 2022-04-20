package banking.web.account

import banking.db.account.Account
import banking.db.account.AccountTable
import banking.db.user.UserTable
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

// create account for each user
// does not WORK WHY IS IT DEPLUICATING
class AccountDto(
    val userId: UUID, //cannot create a  ew UUID for some reasons
    val type: String,
    val balance: Double,
)

fun Route.accounts() {
    route("/accounts") {
        post ("/") {
//            val userId = UUID.fromString(this.context.request.queryParameters["userId"]!!) // from username to id
//            val type = this.context.request.queryParameters["type"]!!

            val account = call.receive<AccountDto>()

           val result = transaction<String> {
                 if (UserTable.select { UserTable.id eq account.userId }.singleOrNull() != null) {


                    Account.new {
                        this.type = account.type
                        this.userId = account.userId
                        this.balance = account.balance
                    }
                    "create new account for this user id success"
                } else{
                    "user id not found"
                }
            }

            this.context.respond(result)

        }
    }
}

fun Route.userAccounts() {
    route("/accounts") {
        get ("/"){
            // val payeeUserId: UUID = UUID.fromString(this.context.request.queryParameters["payeeUserId"]!!) // user id x 1

            val account = call.receive<AccountDto>()

            transaction {
                // inner join user and user with userid as key
                // show all available account id first

                (UserTable innerJoin AccountTable)
//                    .slice(UserTable.id, TransactionTable.payee, TransactionTable.payment)
                    .select {UserTable.id eq account.userId}
                    .forEach{ println("${it[UserTable.userName]}, ${it[UserTable.name]}, ${it[AccountTable.id]}, ${it[AccountTable.type]}") }
            }
            this.context.respond("userAccounts success")
        }
    }

}

// ignore for now
// account and user, keep transaction
fun Route.deleteAccount() {
    route("/deleteAccount") {
        delete {
            val userName: String = this.context.request.queryParameters["userName"]!!
            transaction {
                UserTable.deleteWhere { UserTable.userName eq userName }
            }
        }
    }
}