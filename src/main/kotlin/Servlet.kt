import model.User
import java.io.IOException
import java.sql.SQLException
import javax.servlet.RequestDispatcher
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/")
open class Servlet : HttpServlet() {
    private lateinit var userDB: UserDB

    override fun init() {
        userDB = UserDB()
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.write("go get response")
//        val action: String = request.servletPath
//        try {
//            when (action) {
//                "/new" -> showNewForm(request, response)
//                "/insert" -> insertUser(request, response)
//                "/delete" -> deleteUser(request, response)
//                "/edit" -> showEditForm(request, response)
//                "/update" -> updateUser(request, response)
//                else -> listUser(request, response)
//            }
//        } catch (ex: SQLException) {
//            throw ServletException(ex)
//        }
    }

    @Throws(SQLException::class, IOException::class, ServletException::class)
    private fun listUser(request: HttpServletRequest, response: HttpServletResponse) {
        val listUser: List<User> = userDB.selectAllUsers()
        request.setAttribute("listUser", listUser)
        val dispatcher: RequestDispatcher = request.getRequestDispatcher("user-list.jsp")
        dispatcher.forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    private fun showNewForm(request: HttpServletRequest, response: HttpServletResponse) {
        val dispatcher: RequestDispatcher = request.getRequestDispatcher("user-form.jsp")
        dispatcher.forward(request, response)
    }

    @Throws(SQLException::class, ServletException::class, IOException::class)
    private fun showEditForm(request: HttpServletRequest, response: HttpServletResponse) {
        val id: Int = request.getParameter("id").toInt()
        val existingUser: User = userDB.selectUser(id)
        val dispatcher: RequestDispatcher = request.getRequestDispatcher("user-form.jsp")
        request.setAttribute("user", existingUser)
        dispatcher.forward(request, response)
    }

    @Throws(SQLException::class, IOException::class)
    private fun insertUser(request: HttpServletRequest, response: HttpServletResponse) {
        val name: String = request.getParameter("name")
        val email: String = request.getParameter("email")
        val country: String = request.getParameter("country")
        val newUser = User(-1, name, email, country)
        userDB.insertUser(newUser)
        response.sendRedirect("list")
    }

    @Throws(SQLException::class, IOException::class)
    private fun updateUser(request: HttpServletRequest, response: HttpServletResponse) {
        val id: Int = request.getParameter("id").toInt()
        val name: String = request.getParameter("name")
        val email: String = request.getParameter("email")
        val country: String = request.getParameter("country")
        val book = User(id, name, email, country)
        userDB.updateUser(book)
        response.sendRedirect("list")
    }

    @Throws(SQLException::class, IOException::class)
    private fun deleteUser(request: HttpServletRequest, response: HttpServletResponse) {
        val id: Int = request.getParameter("id").toInt()
        userDB.deleteUser(id)
        response.sendRedirect("list")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}