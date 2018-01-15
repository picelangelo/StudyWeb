<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.service.BeantwortetService" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Beantwortet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page import="at.pichlerlehner.studyweb.service.FragebogenService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("user", benutzer);
    FragebogenService fragebogenService = new FragebogenService();
    BeantwortetService beantwortetService = new BeantwortetService();
    List<Beantwortet> beantwortetList = beantwortetService.findAnswersByUser(benutzer);
    Map<Long, List<Beantwortet>> beantwortetGrouped = beantwortetList.stream().collect(Collectors.groupingBy(x -> x.getFrage().getFragebogen().getPrimaryKey()));
    request.setAttribute("beantwortetGrouped", beantwortetGrouped);
    request.setAttribute("fragebogenService", fragebogenService);
    request.setAttribute("beantwortetService", beantwortetService);
    request.setAttribute("userPercentage", beantwortetService.getPercentageOfUser(benutzer));
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
        Welcome to Studyweb, <c:out value="${user.vorname}"/>
    </h4>
    <h5>
        Your Statistics
    </h5>
    <br/>
    <ul class="collapsible popout" data-collapsible="accordion">
        <c:forEach items="${beantwortetGrouped}" var="beantwortetEntry">
            <li>
                <div class="collapsible-header">
                    <i class="material-icons">code</i>
                    <h6><c:out
                            value="${fragebogenService.findEntityById(beantwortetEntry.key).get().bezeichnung}"/></h6>
                    <span class="new badge" data-badge-caption="%"><c:out
                            value="${fragebogenService.getPercentageByUserAndQuiz(user, fragebogenService.findEntityById(beantwortetEntry.key).get()) * 100}"/></span>
                </div>
                <div class="collapsible-body">
                    <span>
                        <ul class="collection">
                            <c:forEach items="${beantwortetEntry.value}" var="beantwortet">
                                <li class="collection-item">
                                    <c:out value="${beantwortet.frage.frage}"/>
                                    <span class="badge" data-badge-caption="%">
                                        <c:out value="${beantwortetService.getPercentageOfUserAndQuestion(user, beantwortet.frage) * 100}"/>
                                    </span>
                                </li>
                            </c:forEach>
                        </ul>
                    </span>
                </div>
            </li>
        </c:forEach>
        <li>
            <div class="collapsible-header fillPercent">
                <i class="material-icons">data_usage</i>
                <span id="userPercentage">
                    <c:out value="${userPercentage *100}"/>
                </span>
                &nbsp;%
            </div>
            <div class="collapsible-body fillPercent">
                <a class="col s12 btn btn-large waves-effect indigo" href="/statistics?delete=true">reset all statistics</a>
            </div>
        </li>
    </ul>

</div>
<script>
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
<script>
    $(document).ready(function () {
        $(".button-collapse").sideNav();

        var percentage = parseFloat($("#userPercentage").text());
        if (percentage > 75) {
            document.getElementsByClassName("fillPercent")[0].style.backgroundColor = "#aed581";
            document.getElementsByClassName("fillPercent")[1].style.backgroundColor = "#aed581";
        } else if (percentage > 50) {
            document.getElementsByClassName("fillPercent")[0].style.backgroundColor = "#4fc3f7";
            document.getElementsByClassName("fillPercent")[1].style.backgroundColor = "#4fc3f7";
        } else {
            document.getElementsByClassName("fillPercent")[0].style.backgroundColor = "#ee6e73";
            document.getElementsByClassName("fillPercent")[1].style.backgroundColor = "#ee6e73";
        }
    });
</script>
</body>
</html>