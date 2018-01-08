<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
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
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <%= benutzer.getVorname()%>
    </h4>
    <ul>
        <li>
            <a href="/new">Create New Quiz</a>
        </li>
        <li>
            <a href="/show">Show Quizzes</a>
        </li>
    </ul>
</div>
</body>
</html>
