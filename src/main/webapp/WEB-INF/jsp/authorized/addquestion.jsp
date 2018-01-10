<%@ page import="at.pichlerlehner.studyweb.domain.Benutzer" %>
<%@ page import="at.pichlerlehner.studyweb.domain.Fragebogen" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Benutzer benutzer = (Benutzer) request.getSession().getAttribute("USER");
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
<div class="container">
    <h4 class="indigo-text">
        Welcome to Studyweb, <%= benutzer.getVorname()%>
    </h4>
    <h5>
        Add Questions to <%= fragebogen.getBezeichnung() %>
    </h5>
    <form id="new-quiz-form" method="post" action="/addquestion">
        <div class="input-field col s12">
            <input name="frage-name" id="frage-name" type="text">
            <label for="frage-name" class="">Question</label>
        </div>
        <br/>
        <p>
            <input type="checkbox" id="multiple-choice" checked="checked" name="multiple-choice"/>
            <label for="multiple-choice">Multiple-Choice</label>
        </p>
        <div id="multiple-choice-div">
            <p>
                Correct answer:
            </p>
            <p>
                <input name="group1" type="radio" id="cora1" value="cora1"/>
                <label for="cora1">Answer 1</label>
            </p>
            <p>
                <input name="group1" type="radio" id="cora2" value="cora2"/>
                <label for="cora2">Answer 2</label>
            </p>
            <p>
                <input name="group1" type="radio" id="cora3" value="cora3"/>
                <label for="cora3">Answer 3</label>
            </p>
            <p>
                <input name="group1" type="radio" id="cora4" value="cora4"/>
                <label for="cora4">Answer 4</label>
            </p>
            <div class="input-field col s12">
                <input name="answer1" id="answer1" type="text">
                <label for="answer1" class="">Answer 1</label>
            </div>
            <div class="input-field col s12">
                <input name="answer2" id="answer2" type="text">
                <label for="answer2" class="">Answer 2</label>
            </div>
            <div class="input-field col s12">
                <input name="answer3" id="answer3" type="text">
                <label for="answer3" class="">Answer 3</label>
            </div>
            <div class="input-field col s12">
                <input name="answer4" id="answer4" type="text">
                <label for="answer4" class="">Answer 4</label>
            </div>
        </div>
        <div id="text-choice-div">
            <input name="text-answer" id="text-answer" type="text">
            <label for="text-answer" class="">Correct Answer</label>
        </div>
        <br/>
        <button type="submit" name="submit" value="another" class="col s12 btn btn-large waves-effect indigo">Another Question</button>
        <button type="submit" name="submit" value="finished" class="col s12 btn btn-large waves-effect indigo">Finished</button>
    </form>
    <br/>
</div>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>

<script>
    $(document).ready(function () {
        $('#text-choice-div').css("display", "none");
        $('#multiple-choice').change(function () {
            if ($(this).is(':checked')) {
                $('#multiple-choice-div').css("display", "block");
                $('#text-choice-div').css("display", "none");
            } else {
                $('#multiple-choice-div').css("display", "none");
                $('#text-choice-div').css("display", "block");
            }
        });
    });
</script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../../js/materialize.js"></script>
</body>
</html>
