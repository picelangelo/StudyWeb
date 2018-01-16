<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>StudyWeb | Login</title>
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
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="center-align row">
        <h3 class="indigo-text">Welcome to Studyweb</h3>
        <h5 class="indigo-text">Please login to your account</h5>
        <div style="padding: 32px 48px 0px 48px; border: 1px solid #EEE;" class="z-depth-1 grey lighten-4 col s4 offset-s4">

            <form method="post" class="row" action="/login" >
                <div class="col s12">
                    <div class="col s12">
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
                    <!--<label style="float: right;">
                        <a class="pink-text" href="#!"><b>Forgot Password?</b></a>
                    </label>-->
                </div>

                <br>
                <div class="row">
                    <button type="submit" name="btn_login" class="col s12 btn btn-large waves-effect indigo">Login</button>
                </div>
            </form>
        </div>
        <div class="col s12"><a href="/register">Create account</a></div>
    </div>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="../../js/materialize.js"></script>
</body>

</html>