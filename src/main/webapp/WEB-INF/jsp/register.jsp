<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudyWeb | Create Account</title>
    <link rel="shortcut icon" type="image/x-icon" href="../../images/logo.PNG">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../css/materialize.css">
    <link rel="stylesheet" href="../../css/index.css">
</head>

<body>
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <div class="container">
                <a class="brand-logo" href="welcome">Studyweb</a>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="login">Login</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="center-align row">
        <h1 class="indigo-text">Welcome to Studyweb</h1>
        <h5 class="indigo-text">Create your Account Here</h5>
        <div style="padding: 32px 48px 0px 48px; border: 1px solid #EEE;" class="z-depth-1 grey lighten-4 col s4 offset-s4">

            <form method="post" class="row">
                <div class="col s12">
                    <div class="col s12">
                    </div>
                </div>

                <div class="row">
                    <div class="input-field col s12">
                        <input name="firstname" id="firstname" type="text" required>
                        <label for="firstname">Enter your Firstname</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input name="lastname" id="lastname" type="text" required>
                        <label for="lastname">Enter your Lastname</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input class="validate" name="email" id="email" type="email" required>
                        <label for="email" class="">Enter your email</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input class="validate" name="password" id="password" type="password" required>
                        <label for="password" class="">Enter your password</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input class="validate" name="password2" id="password2" type="password" required>
                        <label for="password2" class="">Repeate your password</label>
                    </div>
                </div>

                <br>
                <div class="row">
                    <button type="submit" name="btn_login" class="col s12 btn btn-large waves-effect indigo">Create Account</button>
                </div>
            </form>
        </div>
        <div class="col s12"><a href="/login">Login</a></div>
    </div>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../js/materialize.js"></script>
</body>

</html>