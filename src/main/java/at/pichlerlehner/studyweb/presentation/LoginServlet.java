package at.pichlerlehner.studyweb.presentation;


import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.service.BenutzerService;
import com.google.common.hash.Hashing;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        String email = request.getParameter("email");
        BenutzerService benutzerService = new BenutzerService();
        Optional<Benutzer> benutzer = benutzerService.authorize(email, password);

        if (benutzer.isPresent()) {
            response.getWriter().print("logged in!");
        } else {
            response.getWriter().print("wrong user/password");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
        dispatcher.forward(request, response);
    }
}
