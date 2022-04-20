// exposed
package banking.app

import banking.db.account.AccountTable
import banking.db.transaction.PayeeTransactionTable
import banking.db.transaction.ReceiveTransactionTable
import banking.db.user.UserTable
import banking.web.account.accounts
import banking.web.account.deleteAccount
import banking.web.account.userAccounts
import banking.web.transaction.*
import banking.web.user.users
import banking.web.user.usernames
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level

// entry point
fun main(args:Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// call individual functions
fun Application.module(){
    install(CallLogging) { level = Level.DEBUG }
    install(ContentNegotiation) {
//        json()
        jackson()
    }
//    install(CORS) {
////        method(HttpMethod.Options)
//        header(HttpHeaders.AccessControlAllowOrigin)
////        header(HttpHeaders.XForwardedProto)
////        anyHost()
////        host("my-host")
//        // host("my-host:80")
//        // host("my-host", subDomains = listOf("www"))
//        // host("my-host", schemes = listOf("http", "https"))
////        allowCredentials = true
////        allowNonSimpleContentTypes = true
////        maxAge = Duration.ofDays(1)
//    }

    install(CORS) {
        method(HttpMethod.Post)
        method(HttpMethod.Get)
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true
        host("localhost:3000", listOf("http", "https")) // frontendHost might be "*"
//        logger.info { "CORS enabled for $hosts" }
    }

    // connect to sql
    Database.connect("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=yoyo")
    // , driver = "org.postgresql.Driver"

    // create both tables
    transaction {
        SchemaUtils.create(UserTable)
        SchemaUtils.create(AccountTable)
        SchemaUtils.create(PayeeTransactionTable)
        SchemaUtils.create(ReceiveTransactionTable)
    }


    routing {
        users()
        accounts()
        depositWithdrawByName()
        deleteAccount()
        paymentId()
        paymentName()
        usernames()
        userAccounts()
        netValue()
        displayTransaction()
        displayUsers()
        displayAccounts()

    }
}

// val log = LoggerFactory.getLogger(Application::class.java)




