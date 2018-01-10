<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.service.FragebogenService" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Rechte" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");

    FragebogenService fragebogenService = new FragebogenService();
    List<Fragebogen> fragebogenList = fragebogenService.findQuizByUserAccess(benutzer);

    request.setAttribute("frageboegen", fragebogenList);
%>
<html>
<head>
    <title>StudyWeb | Show</title>
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

    <br/>
    <div class="collection">
        <a class="collection-item" href="#!" style="border-bottom: thin solid gray">
            <span class="badge"><b>Creator</b></span>
            <b>Title</b>
        </a>
        <c:if test="${frageboegen.size() == 0}">
            <a href="#!" class="collection-item">
                No quizzes yet!
            </a>
        </c:if>
        <c:forEach items="${frageboegen}" var="fragebogen">
            <a href="do?quiz=${fragebogen.primaryKey}" class="collection-item">
                <span class="badge">${fragebogen.ersteller.vorname} ${fragebogen.ersteller.nachname}</span>
                ${fragebogen.bezeichnung}
            </a>
        </c:forEach>
    </div>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
</body>
</html>
