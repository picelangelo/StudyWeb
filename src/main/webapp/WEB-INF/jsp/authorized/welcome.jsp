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

    <div class="col s12 m7">
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://i.imgur.com/7Y3Xkgf.png">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p>List all quizzes you have access to.</p>
                </div>
                <div class="card-action">
                    <a class="indigo-text" href="/show">show</a>
                </div>
            </div>
        </div>
    </div>
    <div class="col s12 m7">
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://i.imgur.com/2rCQvJ1.png">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p>Create a new Quiz!</p>
                </div>
                <div class="card-action">
                    <a class="indigo-text" href="/new">create</a>
                </div>
            </div>
        </div>
    </div>
    <div class="col s12 m7">
        <div class="card horizontal">
            <div class="card-image">
                <img src="https://i.imgur.com/OnGno8d.png">
            </div>
            <div class="card-stacked">
                <div class="card-content">
                    <p>Edit a Quiz!</p>
                </div>
                <div class="card-action">
                    <a class="indigo-text" href="#!">edit</a>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
</body>
</html>