<html>
<head>
    <title>StudyWeb</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/logo.PNG">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel='stylesheet' href='css/Varela+Round.css'>

<body>
    <script href="JavaScript/index.js"></script>
    <script href="JavaScript/jquery.validate.min.js"></script>
    <div class="container">
        <div class="text-center" style="padding:50px 0">
            <div class="logo">Welcome to StudyWeb!</div>
            <div class="login-form-1">
                <form id="login-form" class="text-left">
                    <div class="login-form-main-message"></div>
                    <div class="main-login-form">
                        <div class="login-group">
                            <div class="form-group">
                                <label for="lg_username" class="sr-only">Username</label>
                                <input type="text" class="form-control" id="lg_username" name="lg_username" placeholder="username">
                            </div>
                            <div class="form-group">
                                <label for="lg_password" class="sr-only">Password</label>
                                <input type="password" class="form-control" id="lg_password" name="lg_password" placeholder="password">
                            </div>
                            <div class="form-group login-group-checkbox">
                                <input type="checkbox" id="lg_remember" name="lg_remember">
                                <label for="lg_remember">remember</label>
                            </div>
                        </div>
                        <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
                    </div>
                    <div class="etc-login-form">
                        <p>forgot your password? <a href="#">click here</a></p>
                        <p>new user? <a href="#">create new account</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
