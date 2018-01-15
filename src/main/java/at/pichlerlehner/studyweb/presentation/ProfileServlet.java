package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.service.BenutzerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/profile.jsp");
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
            String email = Ensurer.ensureNotBlank(request.getParameter("email_address"));
            String firstName = Ensurer.ensureNotBlank(request.getParameter("first_name"));
            String lastName = Ensurer.ensureNotBlank(request.getParameter("last_name"));
            benutzer.setEmail(email);
            benutzer.setVorname(firstName);
            benutzer.setNachname(lastName);

            BenutzerService benutzerService = new BenutzerService();
            benutzerService.updateEntity(benutzer);
            session.setAttribute("USER", benutzer);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/profile.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
