<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url var="css" value="webapp/WEB-INF/resources/" />
<spring:url var="js" value="/resources/js" />

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Insert title here</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/css/loginForm.css">

</head>

<body>

<!------------------------------------ DIV WITH FORM LOGIN --------------------------------------->

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <a href="#" class="active" id="login-form-link">Login</a>
                    </div>
                    <hr>
                    <p style="color: red">${blad}</p>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">

                            <!--------------------- FORM LOGIN  ------------------->

                            <form id="login-form" action="/login"
                                  method="POST" role="form">
                            <div class="form-group">
                                <label>Login</label>
                                <input type="text" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Adress" value="">
                            </div>
                            <br />
                            <div class="form-group">
                                <label>Password</label> <input type="password" name="password"
                                                               id="password" tabindex="2" class="form-control"
                                                               placeholder="Password">
                            </div>
                            <div class="form-group text-center">
                                <input type="checkbox" tabindex="3" class="" name="remember"
                                       id="remember"> <label for="remember">
                                Remember Me</label>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="login-submit" id="login-submit"
                                               tabindex="4" class="form-control btn btn-login"
                                               value="Zaloguj">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="text-center">
                                            <a href="https://phpoll.com/recover" tabindex="5"
                                               class="forgot-password">Forgot Password?</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </form>

                            <!--------------------- FORM LOGIN  ------------------->

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!------------------------------------ DIV WITH FORM LOGIN --------------------------------------->

<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<%--<script src="${js}/bootstrap.min.js"></script>--%>

</body>
</html>