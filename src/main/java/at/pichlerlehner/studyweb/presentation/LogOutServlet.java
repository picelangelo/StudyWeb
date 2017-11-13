package at.pichlerlehner.studyweb.presentation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("USER", null);
        response.sendRedirect("/login");
    }
}
