package at.pichlerlehner.studyweb.presentation;


import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.service.BenutzerService;
import com.google.common.hash.Hashing;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class LoginServlet extends BaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isLoggedIn(request)) {
            response.sendRedirect("/welcome");
        } else {
            String password = request.getParameter("password");
            password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            String email = request.getParameter("email");
            BenutzerService benutzerService = new BenutzerService();
            Optional<Benutzer> benutzer = benutzerService.authorize(email, password);

            if (benutzer.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute("USER", benutzer.get());
                response.sendRedirect("/welcome");
            } else {
                response.sendRedirect("/login");
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isLoggedIn(request)) {
            response.sendRedirect("/welcome");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
