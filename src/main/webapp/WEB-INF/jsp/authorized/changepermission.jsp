<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.service.FragebogenService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="at.pichlerlehner.studyweb.service.BerechtigungService" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) session.getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());
    FragebogenService fragebogenService = new FragebogenService();
    Fragebogen fragebogen = (Fragebogen) request.getAttribute("fragebogen");
    request.setAttribute("fragebogenService", fragebogenService);
    request.setAttribute("fragebogen", fragebogen);
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
        Edit: <c:out value="${fragebogen.bezeichnung}"/>
    </h5>
    <br/>

    <form method="post">

        <ul class="collapsible popout" data-collapsible="expandable">
            <c:if test="${userlist.size() == 0}">
                <li>
                    <div class="collapsible-header">
                        No other user has access yet!
                    </div>
                </li>
            </c:if>
            <c:forEach items="${userlist}" var="userAc">
                <li>
                    <div class="collapsible-header">
                        <c:out value="${userAc.email}"/>
                        <span class="new badge" data-badge-caption="">
                            <%= fragebogenService.userHasWriteAccess(fragebogen, ((Benutzer)pageContext.getAttribute("userAc"))) ? "write" : "read" %>
                        </span>
                    </div>
                    <div class="collapsible-body">
                    <span>
                        <p>
                            <input type="checkbox" id="revoke${userAc.primaryKey}" name="revoke"
                                   value="${userAc.email}"/>
                            <label for="revoke${userAc.primaryKey}">Revoke rights</label>
                        </p>
                    </span>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <br/>

        <h5>Grant access to new user</h5>
        <div class="input-field col s12">
            <input class="validate" name="email" id="email" type="email">
            <label for="email" class="">Email</label>
        </div>
        <p>
            <input type="checkbox" id="write" name="write" value="true"/>
            <label for="write">Grant write access</label>
        </p>
        <br/>
        <button type="submit" name="submit" value="changePers" class="col s12 btn btn-large waves-effect indigo">Save Changes</button>


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