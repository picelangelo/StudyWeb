<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="at.pichlerlehner.studyweb.service.FragebogenService" %>
<%@ page import="java.util.Optional" %>
<%@ page import="at.pichlerlehner.studyweb.service.FrageService" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Frage" %>
<%@ page import="java.util.List" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Antwort" %>
<%@ page import="at.pichlerlehner.studyweb.service.AntwortService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());
    FragebogenService fragebogenService = new FragebogenService();
    Fragebogen fragebogen = fragebogenService.findEntityById(Long.parseLong(request.getParameter("quiz"))).get();
    FrageService frageService = new FrageService();
    AntwortService antwortService = new AntwortService();
    List<Frage> frageList = frageService.getFragenByFragebogen(fragebogen);
    request.setAttribute("fragebogen", fragebogen);
    request.setAttribute("fragen", frageList);
    request.setAttribute("antwortService", antwortService);
%>

<html>
<head>
    <title>StudyWeb | Welcome</title>
    <link rel="shortcut icon" type="image/x-icon" href="../../../images/logo.PNG">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../../css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../css/index.css">
</head>

<body>
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <div class="container">
                <a class="brand-logo" href="welcome">Studyweb</a>
                <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="profile">Profile</a></li>
                    <li><a href="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<ul id="mobile-demo" class="side-nav">
    <li><a href="profile">Profile</a></li>
    <li><a href="logout">Logout</a></li>
</ul>
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <c:out value="${username}"/>
    </h4>
    <h5>
        Edit <c:out value="${fragebogen.bezeichnung}"/>
    </h5>
    <form method="post">
        <div class="row">
            <div class="input-field col s12">
                <input value="${fragebogen.bezeichnung}" id="quiz_title" type="text" class="validate" name="quiz_title"
                       required>
                <label class="active" for="quiz_title">Quiz Title</label>
            </div>
        </div>
        <ul class="collapsible" data-collapse="accordion">
            <c:forEach items="${fragen}" var="frage">
                <li>
                    <div class="collapsible-header input-field col s12">
                        <input value="${frage.frage}" id="question${frage.primaryKey}" type="text" class="validate"
                               name="frage${frage.primaryKey}"
                               required>
                        <label class="active" for="question${frage.primaryKey}">Question</label>
                    </div>
                    <div class="collapsible-body">
                        <span>
                                <%
                                    String[] checkedStrings = {"", "", "", ""};
                                %>
                            <c:forEach items="${antwortService.getAntwortenByFrage(frage)}" var="antwort">
                                <input value="${antwort.antwort}" id="answer${antwort.primaryKey}" type="text" class="validate"
                                       name="antwort${antwort.primaryKey}"
                                       required>
                                <label class="active" for="answer${antwort.primaryKey}">Antwort</label>
                                <%
                                    List<Antwort> antwortList = antwortService.getAntwortenByFrage((Frage)pageContext.getAttribute("frage"));
                                    for (int i = 0; i < antwortList.size(); i++) {
                                        if (antwortList.get(i).isCorrect()) {
                                            checkedStrings[i] = "checked=\"checked\"";
                                        }
                                    }
                                %>
                            </c:forEach>
                            <br/>
                            <c:if test="${frage.multipleChoice}">
                                <input name="group${frage.primaryKey}" type="radio" id="${frage.primaryKey}1" value="1" <%= checkedStrings[0] %>/>
                                <label for="${frage.primaryKey}1">Antwort 1</label>
                                <input name="group${frage.primaryKey}" type="radio" id="${frage.primaryKey}2" value="2" <%= checkedStrings[1] %>/>
                                <label for="${frage.primaryKey}2">Antwort 2</label>
                                <input name="group${frage.primaryKey}" type="radio" id="${frage.primaryKey}3" value="3" <%= checkedStrings[2] %>/>
                                <label for="${frage.primaryKey}3">Antwort 3</label>
                                <input name="group${frage.primaryKey}" type="radio" id="${frage.primaryKey}5" value="4" <%= checkedStrings[3] %>/>
                                <label for="${frage.primaryKey}4">Antwort 4</label>
                            </c:if>
                        </span>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <button type="submit" name="submit" value="save_changes" class="col s12 btn btn-large waves-effect indigo">Save
            Changes
        </button>
        <!--button type="submit" name="submit" value="add_questions" class="col s12 btn btn-large waves-effect indigo">Add
            Questions
        </button-->
    </form>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
<script>
    $(document).ready(function () {
        $(".button-collapse").sideNav();
    });
</script>
</body>
</html>