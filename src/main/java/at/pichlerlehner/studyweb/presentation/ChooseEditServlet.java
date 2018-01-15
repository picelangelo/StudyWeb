package at.pichlerlehner.studyweb.presentation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: StudyWeb
 * Document: ChooseEditServlet.java
 * Author: Philip
 * Created: 14.01.2018
 */
public class ChooseEditServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/chooseedit.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/chooseedit.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
