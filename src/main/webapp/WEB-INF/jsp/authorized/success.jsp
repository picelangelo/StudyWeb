<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());

    String successMessage = "";
    if (Objects.nonNull(request.getSession().getAttribute("SUCCESS"))) {
        successMessage = (String) request.getSession().getAttribute("SUCCESS");
        session.removeAttribute("SUCCESS");
    }
%>
<html>
<head>
    <title>StudyWeb | Success</title>
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
    <p>
        <b>
            Success!
        </b>
        <% request.setAttribute("success", successMessage); %>
        <c:out value="${success}"/>
    </p>
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
