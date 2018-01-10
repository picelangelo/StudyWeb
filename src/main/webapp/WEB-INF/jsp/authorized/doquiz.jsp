<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int" %>
<%@ page import="at.pichlerlehner.studyweb.persistence.FrageRepo" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Frage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Antwort" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("USER");
    Fragebogen fragebogen = (Fragebogen) session.getAttribute("QUIZ");
    ArrayList<Frage> frageArrayList = (ArrayList<Frage>) session.getAttribute("QUESTIONS");
    Frage frage = frageArrayList.get(0);
    ArrayList<Antwort> antworten = (ArrayList<Antwort>) session.getAttribute("ANSWERS");
    request.setAttribute("fragen", frageArrayList);
    request.setAttribute("antworten", antworten);
    request.setAttribute("mulChoice", frage.isMultipleChoice());
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
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <%= benutzer.getVorname()%>
    </h4>
    <h5>
        Quiz <%= fragebogen.getBezeichnung() %>: Question 1
    </h5>
    <p>
        <%= frage.getFrage()%>
    </p>
    <form method="post" action="/do">
        <c:if test="${mulChoice == true}">
            <c:forEach begin="0" end="${antworten.size()-1}" var="counter">
                <p>
                    <input name="answers" type="radio" id="cora${counter}"/>
                    <label for="cora${counter}">${antworten.get(counter).antwort}</label>
                </p>
            </c:forEach>
        </c:if>
        <br/>
        <c:if test="${mulChoice == false}">
            <div class="input-field col s12">
                <input class="validate" name="your-answer" id="your-answer" type="text">
                <label for="your-answer" class="">Your Answer</label>
            </div>
        </c:if>

        <div class="row" style="margin: auto;">
            <c:forEach begin="0" end="${fragen.size()-1}" var="counter">
                <div class="col s1" style="background-color: #3d5afe; margin: 1px; border: thin solid black">
                    &nbsp;&nbsp;&nbsp;
                </div>
            </c:forEach>
        </div>
        <br/>
        <div class="row">
            <button type="submit" name="btn_submit" class="col s1 btn btn-large waves-effect indigo" style="margin: 5px">Previous</button>
            <button type="submit" name="btn_submit" class="col s1 btn btn-large waves-effect indigo" style="margin: 5px">Next</button>
        </div>
    </form>

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="../../../js/materialize.js"></script>
</div>
</body>
</html>
