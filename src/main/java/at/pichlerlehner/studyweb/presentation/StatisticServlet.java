package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Beantwortet;
import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.service.BeantwortetService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StatisticServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            if (Objects.nonNull(request.getParameter("delete")) && request.getParameter("delete").equals("true")) {
                BeantwortetService beantwortetService = new BeantwortetService();
                Benutzer benutzer = (Benutzer)request.getSession().getAttribute("USER");
                beantwortetService.deleteByUser(benutzer);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/statistics.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
