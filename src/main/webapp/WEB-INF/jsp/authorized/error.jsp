<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");

    String errorMessage = "";
    if (Objects.nonNull(request.getSession().getAttribute("ERROR"))) {
        errorMessage = (String) request.getSession().getAttribute("ERROR");
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
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <%= benutzer.getVorname()%>
    </h4>
    <p>
        <b>
            Error!
        </b>
        <%= errorMessage %>
    </p>
</div>
</body>
</html>
