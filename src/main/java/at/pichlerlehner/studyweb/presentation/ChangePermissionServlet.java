package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.*;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project: StudyWeb
 * Document: ChooseEditServlet.java
 * Author: Philip
 * Created: 14.01.2018
 */
public class ChangePermissionServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            Benutzer benutzer = (Benutzer) session.getAttribute("USER");
            BerechtigungService berechtigungService = new BerechtigungService();
            FragebogenService fragebogenService = new FragebogenService();
            Optional<Fragebogen> optFragebogen = fragebogenService.findEntityById(Long.parseLong(request.getParameter("quiz")));
            if (!optFragebogen.isPresent()) {
                session.setAttribute("ERROR", "Quiz not found");
                response.sendRedirect("/error");
                return;
            }
            if (!fragebogenService.userHasAccess(optFragebogen.get(), benutzer)) {
                response.sendError(401, "You are not authorized to edit the permissions of this quiz!");
                return;
            }

            ArrayList<Benutzer> benutzerArrayList = new ArrayList<>(berechtigungService.findUserAccessToQuiz(optFragebogen.get()));
            request.setAttribute("userlist", benutzerArrayList);
            request.setAttribute("fragebogen", optFragebogen.get());

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/changepermission.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            String email = request.getParameter("email");
            boolean writeAccess = Objects.nonNull(request.getParameter("write"));
            FragebogenService fragebogenService = new FragebogenService();
            BenutzerService benutzerService = new BenutzerService();
            BerechtigungService berechtigungService = new BerechtigungService();
            Benutzer benutzer = (Benutzer) session.getAttribute("USER");
            Optional<Fragebogen> optFragebogen = fragebogenService.findEntityById(Long.parseLong(request.getParameter("quiz")));
            if (!optFragebogen.isPresent()) {
                session.setAttribute("ERROR", "Quiz not found");
                response.sendRedirect("/error");
                return;
            }
            if (!fragebogenService.userHasAccess(optFragebogen.get(), benutzer)) {
                response.sendError(401, "You are not authorized to edit the permissions of this quiz!");
                return;
            }

            if (!email.trim().equals("")) {
                Optional<Benutzer> userTo = benutzerService.findByEmail(email);
                if (!userTo.isPresent()) {
                    session.setAttribute("ERROR", "User not found");
                    response.sendRedirect("/error");
                    return;
                }
                if (fragebogenService.userHasAccess(optFragebogen.get(), userTo.get())) {
                    session.setAttribute("ERROR", "User already has access");
                    response.sendRedirect("/error");
                    return;
                }

                Berechtigung berechtigung = new Berechtigung();
                berechtigung.setBenutzer(userTo.get());
                berechtigung.setDarfBearbeiten(writeAccess);
                berechtigung.setFragebogen(optFragebogen.get());
                berechtigungService.saveEntity(berechtigung);

            }

            String[] revokes = request.getParameterValues("revoke");
            if (Objects.nonNull(revokes) && revokes.length > 0) {
                for (String s : revokes) {
                    Optional<Benutzer> userRevoke = benutzerService.findByEmail(s);
                    if (!userRevoke.isPresent()) {
                        session.setAttribute("ERROR", "User not found");
                        response.sendRedirect("/error");
                        return;
                    }
                    List<Berechtigung> berechtigungList = berechtigungService.findByUserAndQuiz(optFragebogen.get(), userRevoke.get());
                    for (Berechtigung berechtigung : berechtigungList) {
                        berechtigungService.deleteEntity(berechtigung);
                    }
                }
            }

            ArrayList<Benutzer> benutzerArrayList = new ArrayList<>(berechtigungService.findUserAccessToQuiz(optFragebogen.get()));
            request.setAttribute("userlist", benutzerArrayList);
            request.setAttribute("fragebogen", optFragebogen.get());
            request.setAttribute("fragebogenService", fragebogenService);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/changepermission.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
