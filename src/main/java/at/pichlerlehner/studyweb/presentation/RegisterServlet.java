package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.service.BenutzerService;
import com.google.common.hash.Hashing;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class RegisterServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isLoggedIn(request)) {
            response.sendRedirect("/welcome");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if(password.equals(password2)){
            String hashpwd = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            Benutzer benutzer = new Benutzer();
            benutzer.setVorname(firstname);
            benutzer.setNachname(lastname);
            benutzer.setEmail(email);
            benutzer.setPassword(hashpwd);

            BenutzerService benutzerService = new BenutzerService();
            benutzerService.createEntity(benutzer);

            Optional<Benutzer> authBenutzer = benutzerService.authorize(email, hashpwd);

            if (authBenutzer.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute("USER", authBenutzer.get());
                response.sendRedirect("/welcome");
            } else {
                response.sendRedirect("/register");
            }

        }else{
            response.sendRedirect("/login");
        }
    }
}
