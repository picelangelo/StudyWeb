package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Frage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ResultServlet extends BaseServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
            return;
        }
        HttpSession session = request.getSession();
        HashMap<Integer, String> hashMap = (HashMap<Integer, String>) session.getAttribute("ANSWERHM");
        if (Objects.isNull(hashMap)) {
            session.setAttribute("ERROR", "No answers given");
            response.sendRedirect("/error");
            return;
        }

        ArrayList<Frage> frageArrayList = (ArrayList<Frage>) session.getAttribute("QUESTIONS");
        ArrayList<Antwort> antwortArrayList = (ArrayList<Antwort>) session.getAttribute("ANSWERS");
        if (Objects.isNull(frageArrayList) || Objects.isNull(antwortArrayList)) {
            session.setAttribute("ERROR", "No result to display");
            response.sendRedirect("/error");
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/resultSite.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
