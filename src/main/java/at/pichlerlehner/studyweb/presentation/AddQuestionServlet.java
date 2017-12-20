package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import com.google.common.collect.Lists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Project: StudyWeb
 * Document: AddQuestionServlet.java
 * Author: Philip
 * Created: 13.12.2017
 */
public class AddQuestionServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Fragebogen fragebogen = (Fragebogen) session.getAttribute("QUIZ");
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else if (Objects.isNull(fragebogen)) {
            response.sendRedirect("/new");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addquestion.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Fragebogen fragebogen = (Fragebogen) session.getAttribute("QUIZ");
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else if (Objects.isNull(fragebogen)) {
            response.sendRedirect("/new");
        } else {
            Frage frage = new Frage();
            frage.setFrage(request.getParameter("frage-name"));
            boolean mulChoice = request.getParameter("multiple-choice") != null;
            frage.setMultipleChoice(mulChoice);
            String correctAnswer = request.getParameter("group1");
            frage.setFragebogen(fragebogen);
            List<Antwort> antworten = new ArrayList<>();
            if (mulChoice) {
                Antwort a1 = new Antwort(request.getParameter("answer1"), correctAnswer.equals("cora1"), frage);
                Antwort a2 = new Antwort(request.getParameter("answer2"), correctAnswer.equals("cora2"), frage);
                Antwort a3 = new Antwort(request.getParameter("answer3"), correctAnswer.equals("cora3"), frage);
                Antwort a4 = new Antwort(request.getParameter("answer4"), correctAnswer.equals("cora4"), frage);
                antworten.addAll(Arrays.asList(a1, a2, a3, a4));
            } else {
                Antwort a1 = new Antwort(request.getParameter("text-answer"), true, frage);
                antworten.add(a1);
            }

            List<Frage> fragen = Arrays.asList(frage);
            if (Objects.isNull(session.getAttribute("QUESTIONS"))) {
                session.setAttribute("QUESTIONS", fragen);
            } else {
                List<Frage> sessionFragen = ((List<Frage>) session.getAttribute("QUESTIONS"));
                session.removeAttribute("QUESTIONS");
                fragen.addAll(sessionFragen);
                session.setAttribute("QUESTIONS", fragen);
            }
            if (Objects.isNull(session.getAttribute("ANSWERS"))) {
                session.setAttribute("ANSWERS", antworten);
            } else {
                ArrayList<Antwort> sessionAntworten = ((ArrayList<Antwort>) session.getAttribute("ANSWERS"));
                session.removeAttribute("ANSWERS");
                antworten.addAll(sessionAntworten);
                session.setAttribute("ANSWERS", antworten);
            }

            if (request.getParameter("submit").equals("finished")) {
                session.removeAttribute("QUIZ");
                session.removeAttribute("QUESTIONS");
                session.removeAttribute("ANSWERS");
                response.sendRedirect("/welcome");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/addquestion.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
