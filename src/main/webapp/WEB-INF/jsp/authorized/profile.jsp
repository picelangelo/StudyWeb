<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());
    request.setAttribute("user", benutzer);
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
                    <li class="active"><a href="profile" >Profile</a></li>
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
    <br/>
    <form action="/profile" method="post">
        <div class="row">
            <div class="input-field col s6">
                <input value="${user.email}" id="email_address" type="text" class="validate" name="email_address" required>
                <label class="active" for="email_address">Email address</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input value="${user.vorname}" id="first_name" type="text" class="validate" name="first_name" required>
                <label class="active" for="first_name">First Name</label>
            </div>
            <div class="input-field col s6">
                <input value="${user.nachname}" id="last_name" type="text" class="validate" name="last_name" required>
                <label class="active" for="last_name">Last Name</label>
            </div>
        </div>
        <a  class="col s12 btn btn-large waves-effect indigo" href="/password">Change Password</a>
        <button type="submit" name="save_changes" class="col s12 btn btn-large waves-effect indigo">Save Changes</button>
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