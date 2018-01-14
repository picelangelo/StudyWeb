<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Frage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Antwort" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());

    request.setAttribute("answerHashMap", session.getAttribute("ANSWERHM"));
    request.setAttribute("questionAL", session.getAttribute("QUESTIONS"));
    request.setAttribute("richtigeAntworten", session.getAttribute("CORRECTANSWERS"));
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
        Welcome to Studyweb, <c:out value="${username}"/>
    </h4>

    <ul class="collapsible" data-collapsible="accordion">
        <c:forEach items="${answerHashMap}" var="entry">
            <li class="answerObject">
                <div class="collapsible-header">
                    <i class="material-icons">question_answer</i>
                    <b><c:out value="${questionAL.get(entry.key).frage}"/></b>
                </div>
                <div class="collapsible-body">
                    <span>
                        <b>Correct Answer:</b>
                        <span class="cora"><c:out value="${richtigeAntworten.get(entry.key).antwort}"/></span>
                        <br/>
                        <b>Your Answer:</b>
                        <span class="usra"><c:out value="${entry.value}"/></span>
                    </span>
                </div>
            </li>
        </c:forEach>
    </ul>

</div>
<script>
    var answerObjects = document.getElementsByClassName('answerObject');
    for (var i = 0; i < answerObjects.length; i++) {
        if (answerObjects.item(i).getElementsByClassName("cora")[0].innerHTML.toLowerCase() === answerObjects.item(i).getElementsByClassName("usra")[0].innerHTML.toLowerCase()) {
            answerObjects[i].getElementsByClassName("collapsible-header")[0].style.backgroundColor = "#aed581";
            answerObjects[i].style.backgroundColor = "#aed581"
        } else {
            answerObjects[i].getElementsByClassName("collapsible-header")[0].style.backgroundColor = "#ee6e73";
            answerObjects[i].style.backgroundColor = "#ee6e73"
        }
    }

    $(document).ready(function () {
        $('.collapsible').collapsible();
    });
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
</body>
</html>