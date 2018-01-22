package at.pichlerlehner.studyweb.presentation;

import at.pichlerlehner.studyweb.domain.Antwort;
import at.pichlerlehner.studyweb.domain.Benutzer;
import at.pichlerlehner.studyweb.domain.Frage;
import at.pichlerlehner.studyweb.domain.Fragebogen;
import at.pichlerlehner.studyweb.foundation.Ensurer;
import at.pichlerlehner.studyweb.service.AntwortService;
import at.pichlerlehner.studyweb.service.FrageService;
import at.pichlerlehner.studyweb.service.FragebogenService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EditQuizServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLoggedIn(request)) {
            response.sendRedirect("/login");
        } else {
            HttpSession session = request.getSession();
            Benutzer benutzer = (Benutzer) session.getAttribute("USER");
            FragebogenService fragebogenService = new FragebogenService();

            Long quizpk;
            try {
                quizpk = Long.parseLong(request.getParameter("quiz"));
            } catch (Exception e) {
                session.setAttribute("ERROR", "quiz not found");
                response.sendRedirect("/error");
                return;
            }
            Optional<Fragebogen> optionalFragebogen = fragebogenService.findEntityById(quizpk);
            if (!optionalFragebogen.isPresent()) {
                session.setAttribute("ERROR", "quiz not found");
                response.sendRedirect("/error");
                return;
            }
            if (!fragebogenService.userHasWriteAccess(optionalFragebogen.get(), benutzer)) {
                response.sendError(401, "You are not authorized to edit this quiz!");
                return;
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/authorized/edit.jsp");
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
            FragebogenService fragebogenService = new FragebogenService();

            Long quizpk;
            try {
                quizpk = Long.parseLong(request.getParameter("quiz"));
            } catch (Exception e) {
                session.setAttribute("ERROR", "Quiz Not Found");
                response.sendRedirect("/error");
                return;
            }
            Optional<Fragebogen> optionalFragebogen = fragebogenService.findEntityById(quizpk);
            if (!optionalFragebogen.isPresent()) {
                session.setAttribute("ERROR", "quiz not found");
                response.sendRedirect("/error");
                return;
            }
            FrageService frageService = new FrageService();
            AntwortService antwortService = new AntwortService();
            List<String> parameterNames = Collections.list(request.getParameterNames());
            for (String parameterName : parameterNames) {
                if (parameterName.startsWith("frage")) {
                    List<Frage> frageList = new ArrayList<>();
                    Long frageKey = Long.parseLong(parameterName.substring(5));
                    Optional<Frage> frage = frageService.findEntityById(frageKey);
                    if (frage.isPresent()) {
                        frage.get().setFrage(request.getParameter(parameterName));
                        frageList.add(frage.get());
                    } else {
                        session.setAttribute("ERROR", "cannot update frage");
                        response.sendRedirect("/error");
                        return;
                    }
                    frageService.saveAll(frageList);

                } else if (parameterName.startsWith("antwort")) {
                    List<Antwort> antwortList = new ArrayList<>();
                    Long questionKey = Long.parseLong(parameterName.substring(7));
                    Optional<Antwort> antwort = antwortService.findEntityById(questionKey);
                    if (antwort.isPresent()) {
                        antwort.get().setAntwort(request.getParameter(parameterName));
                        antwortList.add(antwort.get());
                    } else {
                        session.setAttribute("ERROR", "cannot update frage");
                        response.sendRedirect("/error");
                        return;
                    }
                    antwortService.saveAll(antwortList);
                } else if (parameterName.startsWith("group")) {
                    Long frageKey = Long.parseLong(parameterName.substring(5));
                    Optional<Frage> frage = frageService.findEntityById(frageKey);
                    if (!frage.isPresent()) {
                        session.setAttribute("ERROR", "cannot update frage");
                        response.sendRedirect("/error");
                        return;
                    }
                    List<Antwort> radioAntwortList = antwortService.getAntwortenByFrage(frage.get());
                    String correctAnswer = request.getParameter(parameterName);
                    if (radioAntwortList.size() == 4) {
                        switch (correctAnswer) {
                            case "1": {
                                radioAntwortList.get(0).setCorrect(true);
                                radioAntwortList.get(1).setCorrect(false);
                                radioAntwortList.get(2).setCorrect(false);
                                radioAntwortList.get(3).setCorrect(false);
                                break;
                            }
                            case "2": {
                                radioAntwortList.get(0).setCorrect(false);
                                radioAntwortList.get(1).setCorrect(true);
                                radioAntwortList.get(2).setCorrect(false);
                                radioAntwortList.get(3).setCorrect(false);
                                break;
                            }
                            case "3": {
                                radioAntwortList.get(0).setCorrect(false);
                                radioAntwortList.get(1).setCorrect(false);
                                radioAntwortList.get(2).setCorrect(true);
                                radioAntwortList.get(3).setCorrect(false);
                                break;
                            }
                            case "4": {
                                radioAntwortList.get(0).setCorrect(false);
                                radioAntwortList.get(1).setCorrect(false);
                                radioAntwortList.get(2).setCorrect(false);
                                radioAntwortList.get(3).setCorrect(true);
                                break;
                            }
                        }
                    }
                    antwortService.saveAll(radioAntwortList);
                }
            }
            optionalFragebogen.get().setBezeichnung(Ensurer.ensureNotBlank(request.getParameter("quiz_title")));
            fragebogenService.saveEntity(optionalFragebogen.get());
            doGet(request,response);
        }
    }
}
