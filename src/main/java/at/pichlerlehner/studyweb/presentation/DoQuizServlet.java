package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.persistence.FrageRepo;
import at.pichlerlehner.studyweb.service.AntwortService;
import at.pichlerlehner.studyweb.service.FrageService;
import at.pichlerlehner.studyweb.service.FragebogenService;
import org.opendof.core.oal.DOFRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class DoQuizServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            if (Objects.isNull(request.getParameter("quiz"))) {
                response.sendRedirect("/show");
            } else {
                HttpSession session = request.getSession();
                FragebogenService fragebogenService = new FragebogenService();
                FrageService frageService = new FrageService();
                AntwortService antwortService = new AntwortService();
                Long quizPK = Long.parseLong(request.getParameter("quiz"));
                Fragebogen fragebogen;
                if (fragebogenService.findEntityById(quizPK).isPresent()) {
                    fragebogen = fragebogenService.findEntityById(quizPK).get();
                    session.setAttribute("QUIZ", fragebogen);

                    ArrayList<Frage> frageArrayList;
                    frageArrayList = new ArrayList<>(frageService.getFragenByFragebogen(fragebogen));
                    session.setAttribute("QUESTIONS", frageArrayList);

                    ArrayList<Antwort> antwortArrayList;
                    antwortArrayList = new ArrayList<>(antwortService.getAntwortenByFragebogen(fragebogen));
                    session.setAttribute("ANSWERS", antwortArrayList);

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/doquiz.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    session.setAttribute("ERROR", String.format("Der Fragebogen mit dem Schlüssel %d wurde konnte nicht gefunden werden.", quizPK));
                    response.sendRedirect("/error");
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            if (!request.getParameter("submit").equals("previous")) {
                Integer parsedValue = request.getParameter("question") != null ? Integer.parseInt(request.getParameter("question")) : null;
                Optional<Integer> optQNumber = Optional.ofNullable(parsedValue);
                Integer qNumber = optQNumber.orElse(0);
                if (Objects.nonNull(session.getAttribute("QUESTIONS"))) {
                    Optional<Object> nullableObject = Optional.ofNullable(session.getAttribute("ANSWERHM"));
                    HashMap<Integer, String> answerHashMap = nullableObject.isPresent() ? (HashMap<Integer, String>) nullableObject.get() : new HashMap<>();

                    //Bei Frage 0 auf jedenfall eine neue HashMap erstellen
                    if (qNumber == 0)
                        answerHashMap = new HashMap<>();

                    if (Objects.nonNull(request.getParameter("answers"))) {
                        answerHashMap.put(qNumber, request.getParameter("answers"));
                    } else if (Objects.nonNull(request.getParameter("your-answer"))) {
                        answerHashMap.put(qNumber, request.getParameter("your-answer"));
                    }

                    session.setAttribute("USERANSWERS", answerHashMap);

                    if (!request.getParameter("submit").equals("finished")) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/doquiz.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        response.sendRedirect("/result");
                    }
                } else {
                    session.setAttribute("ERROR", "No quiz chosen");
                    response.sendRedirect("/error");
                }
            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/doquiz.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}
