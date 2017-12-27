package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Fragebogen;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Project: StudyWeb
 * Document: NewQuizServlet.java
 * Author: Philip
 * Created: 29.11.2017
 */
public class NewQuizServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/newquiz.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            Benutzer user = (Benutzer) session.getAttribute("USER");
            String title = request.getParameter("quiz-title");
            if (title == null || title.isEmpty()) {
                response.sendRedirect("/new");
            } else {
                Fragebogen fragebogen = new Fragebogen(title, user);
                session.setAttribute("QUIZ", fragebogen);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/addquestion.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
