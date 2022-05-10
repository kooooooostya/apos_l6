import model.User
import java.io.File

fun main() {
    val db = UserDB()

//    db.deleteUser(2)
    println(db.selectAllUsers())
}