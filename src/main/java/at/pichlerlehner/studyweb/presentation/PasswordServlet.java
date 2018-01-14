package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.service.BenutzerService;
import com.google.common.hash.Hashing;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PasswordServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/password.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            Benutzer benutzer = (Benutzer) session.getAttribute("USER");
            String oldPassword = Ensurer.ensureNotBlank(request.getParameter("old_password"));
            String password1 = Ensurer.ensureNotBlank(request.getParameter("new_password"));
            String password2 = Ensurer.ensureNotBlank(request.getParameter("repeat_password"));

            oldPassword = Hashing.sha256().hashString(oldPassword, StandardCharsets.UTF_8).toString();
            password1 = Hashing.sha256().hashString(password1, StandardCharsets.UTF_8).toString();
            password2 = Hashing.sha256().hashString(password2, StandardCharsets.UTF_8).toString();

            if (!benutzer.getPassword().equals(oldPassword)) {
                session.setAttribute("ERROR", "Old password not correct!");
                response.sendRedirect("/error");
                return;
            }

            if (!password1.equals(password2)) {
                session.setAttribute("ERROR", "Passwords not identical");
                response.sendRedirect("/error");
                return;
            }

            benutzer.setPassword(password1);
            BenutzerService benutzerService = new BenutzerService();
            benutzerService.updateEntity(benutzer);
            session.setAttribute("USER", benutzer);

            session.setAttribute("SUCCESS", "Successfully updated password");
            response.sendRedirect("/success");
        }
    }
}
