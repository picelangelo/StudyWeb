package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.*;
import at.pichlerlehner.studyweb.persistence.FrageRepo;
import at.pichlerlehner.studyweb.service.AntwortService;
import at.pichlerlehner.studyweb.service.BeantwortetService;
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
import java.util.stream.Collectors;

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
                Long quizPK;
                try {
                    quizPK = Long.parseLong(request.getParameter("quiz"));
                } catch (Exception e) {
                    session.setAttribute("ERROR", "Quiz not found");
                    response.sendRedirect("/error");
                    return;
                }
                Fragebogen fragebogen;
                if (fragebogenService.findEntityById(quizPK).isPresent()) {
                    fragebogen = fragebogenService.findEntityById(quizPK).get();

                    if (!fragebogenService.userHasAccess(fragebogen, (Benutzer) session.getAttribute("USER"))) {
                        response.sendError(401, "You are not authorized to view this quiz!");
                        return;
                    }

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
                if (Objects.isNull(request.getParameter("question"))) {
                    session.setAttribute("ERROR", "No question chosen");
                    response.sendRedirect("/error");
                    return;
                }

                Integer qNumber = Integer.parseInt(request.getParameter("question"));
                qNumber--;

                if (Objects.nonNull(session.getAttribute("QUESTIONS"))) {
                    Optional<Object> nullableObject = Optional.ofNullable(session.getAttribute("ANSWERHM"));
                    HashMap<Integer, String> answerHashMap = nullableObject.isPresent() ? (HashMap<Integer, String>) nullableObject.get() : new HashMap<>();

                    if (Objects.nonNull(request.getParameter("answers"))) {
                        answerHashMap.put(qNumber, request.getParameter("answers"));
                    } else if (Objects.nonNull(request.getParameter("your-answer"))) {
                        answerHashMap.put(qNumber, request.getParameter("your-answer"));
                    }

                    session.setAttribute("ANSWERHM", answerHashMap);

                    if (!request.getParameter("submit").equals("finished")) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/doquiz.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        ArrayList<Frage> frageArrayList = (ArrayList<Frage>) session.getAttribute("QUESTIONS");
                        ArrayList<Antwort> richtigeAntworten = new ArrayList<>(((ArrayList<Antwort>) session.getAttribute("ANSWERS")).stream().filter(x -> x.isCorrect()).collect(Collectors.toList()));
                        session.setAttribute("CORRECTANSWERS", richtigeAntworten);

                        if (frageArrayList.size() != answerHashMap.size()) {
                            session.setAttribute("ERROR", "You have to answer every question");
                            response.sendRedirect("/error");
                            return;
                        }
                        if (richtigeAntworten.size() != answerHashMap.size()) {
                            session.setAttribute("ERROR", "internal error");
                            response.sendRedirect("/error");
                            return;
                        }
                        BeantwortetService beantwortetService = new BeantwortetService();
                        Benutzer benutzer = (Benutzer) session.getAttribute("USER");

                        for (int i = 0; i < frageArrayList.size(); i++) {
                            boolean check = richtigeAntworten.get(i).getAntwort().toLowerCase().equals(answerHashMap.get(i).toLowerCase());
                            beantwortetService.newBeantwortet(benutzer, frageArrayList.get(i), check);
                        }
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
