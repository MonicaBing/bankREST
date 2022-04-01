package banking.web.user

import banking.db.user.User
import banking.db.user.UserTable
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.NullPointerException

// users
class UserDto(
    val userName: String,
    val name: String
)

// create new users
fun Route.users() {
    route("/users") {
        post("/") {
            //---- REST API bit
            // 1. add in body in client request, with the variables and type
            // 2. get the body like below and do the same trannsaction
            val user =call.receive<UserDto>()

            transaction {
                User.new {
                    this.userName = user.userName
                    this.name = user.name
                    // new uuid - ID - i.e. auto
                }
            }
            this.context.respond("create users success")
        }
    }
}

// update user name

class UsernameDto(
    val oldName: String = "",
    val currentUserName: String = "" ,
    val newName: String = ""
)

fun Route.usernames() {
    route("/users") {
        // update
        put ("/{userName}"){
//            val oldName: String = this.context.request.queryParameters["oldName"]!!
//            val currentUserName: String = this.context.request.queryParameters["current_userName"]!!
//            val newName: String = this.context.request.queryParameters["newName"]!!
            val username= call.receive<UserDto>()

            val oldUserName: String = this.context.parameters["userName"]!!

            transaction {
                // old parameters - URL
                UserTable.update({ UserTable.userName eq oldUserName }) {
                    it[name] = username.userName
                }

//                AccountTable.update({(AccountTable.userName eq oldUserName) and (AccountTable.name eq currentName)}){
//                    it[userName] = newUserName
//                }
            }
            this.context.respond("update user name success")
        }
    }
}