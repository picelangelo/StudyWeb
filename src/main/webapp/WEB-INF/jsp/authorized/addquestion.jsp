<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
    request.setAttribute("username", benutzer.getVorname());
    Fragebogen fragebogen = (Fragebogen) request.getSession().getAttribute("QUIZ");
%>
<html>
<head>
    <title>StudyWeb | Add Questions</title>
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
        <% request.setAttribute("quizBezeichnung", fragebogen.getBezeichnung()); %>
        Add Questions to <c:out value="${quizBezeichnung}"/>
    </h5>
    <form id="new-quiz-form" method="post" action="/addquestion">
        <div class="input-field col s12">
            <input name="frage-name" id="frage-name" type="text" required>
            <label for="frage-name" class="">Question</label>
        </div>
        <br/>
        <p>
            <input type="checkbox" id="multiple-choice" checked="checked" name="multiple-choice"/>
            <label for="multiple-choice">Multiple-Choice</label>
        </p>
        <div id="multiple-choice-div">
            <div class="row">
                <div class="input-field col s11">
                    <input name="answer1" id="answer1" type="text" class="mcAnswer">
                    <label for="answer1" class="">Answer 1</label>
                </div>
                <div class="col s1">
                    <p>
                        <input name="group1" type="radio" id="cora1" value="cora1"/>
                        <label for="cora1">Correct</label>
                    </p>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s11">
                    <input name="answer2" id="answer2" type="text" class="mcAnswer">
                    <label for="answer2" class="">Answer 2</label>
                </div>
                <div class="col s1">
                    <p>
                        <input name="group1" type="radio" id="cora2" value="cora2"/>
                        <label for="cora2">Correct</label>
                    </p>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s11">
                    <input name="answer3" id="answer3" type="text" class="mcAnswer">
                    <label for="answer3" class="">Answer 3</label>
                </div>
                <div class="col s1">
                    <p>
                        <input name="group1" type="radio" id="cora3" value="cora3"/>
                        <label for="cora3">Correct</label>
                    </p>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s11">
                    <input name="answer4" id="answer4" type="text" class="mcAnswer">
                    <label for="answer4" class="">Answer 4</label>
                </div>
                <div class="col s1">
                    <p>
                        <input name="group1" type="radio" id="cora4" value="cora4"/>
                        <label for="cora4">Correct</label>
                    </p>
                </div>
            </div>
        </div>
        <div id="text-choice-div">
            <input name="text-answer" id="text-answer" type="text">
            <label for="text-answer" class="">Correct Answer</label>
        </div>
        <br/>
        <button type="submit" name="submit" value="another" class="col s12 btn btn-large waves-effect indigo">Another
            Question
        </button>
        <button type="submit" name="submit" value="finished" class="col s12 btn btn-large waves-effect indigo">
            Finished
        </button>
    </form>
    <br/>
</div>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>

<script>
    $(document).ready(function () {
        $('#text-choice-div').css("display", "none");
        $('.mcAnswer').prop('required', true);
        $('#text-answer').prop('required', false);
        $('#cora1').prop('required', true);
        $('#multiple-choice').change(function () {
            if ($(this).is(':checked')) {
                $('#multiple-choice-div').css("display", "block");
                $('#text-choice-div').css("display", "none");
                $('.mcAnswer').prop('required', true);
                $('#text-answer').prop('required', false);
                $('#cora1').prop('required', true);
            } else {
                $('#multiple-choice-div').css("display", "none");
                $('#text-choice-div').css("display", "block");
                $('.mcAnswer').prop('required', false);
                $('#text-answer').prop('required', true);
                $('#cora1').prop('required', false);
            }
        });
    });
</script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>

<script>
    $(document).ready(function () {
        $(".button-collapse").sideNav();
    });
</script>
</body>
</html>
