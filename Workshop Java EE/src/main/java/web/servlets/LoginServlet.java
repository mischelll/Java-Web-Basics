package web.servlets;

import entities.service.UserLoginServiceModel;
import services.UsersService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/users/login")
public class LoginServlet extends HttpServlet {
    private final UsersService usersService;

    @Inject
    public LoginServlet(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(true);
        // Set some attribute values to the session
        // In this case user and password from the request and client
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        System.out.println();

        UserLoginServiceModel login = this.usersService.login(username, password);
        if(login == null){
            resp.sendRedirect("/users/login");
        }
        resp.sendRedirect("/home");
    }
}
