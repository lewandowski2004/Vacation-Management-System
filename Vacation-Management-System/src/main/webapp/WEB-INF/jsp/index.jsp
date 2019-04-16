<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/css/loginForm.css">
    <link rel="stylesheet" href="/css/applicatios.css">
</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>
<div class="container">
    <div id="addEmployee" class="row">
        <security:authorize access="hasAuthority('ROLE_ADMIN')">

        <div class="col-md-8 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Powiadomienia Systemu</h3>
                            <hr>
                            <div class="col-lg-4">
                                <div id="powiadomienia-systemu-hover" class="panel panel-login">
                                    <a href="/admin/vacationBalanceEmployees">
                                    <div class="panel-body">
                                        <p style="font-size: 17px;text-align: center">Bilansy Urlopowe</p>
                                        <p style="text-align: center"><span style="font-size: 45px; ${color}" class="${glyphicon}" aria-hidden="true"></span></p>
                                        <p style="font-size: 13px;text-align: center">${content}</p>
                                    </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </security:authorize>
        <div class="col-md-8 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Statystyki Twojego urlopu</h3>
                            <hr>
                            <div class="col-lg-4">
                                <div class="panel panel-login">
                                    <div class="panel-body">
                                        <p style="font-size: 17px;text-align: center">Dostępny urlop</p>
                                        <p style="text-align: center"><span style="font-size: 45px;color: aqua" class="glyphicon glyphicon-stats" aria-hidden="true"></span></p>
                                        <p style="font-size: 17px;text-align: center">[dni]</p>
                                        <p style="text-align: center; font-size: 30px"><%--${vacationLimit}--%>${vacationLimit}/${annualVacation}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="panel panel-login">
                                    <div class="panel-body">
                                        <p style="font-size: 17px;text-align: center">Urlop wypoczynkowy</p>
                                        <p style="text-align: center"><span style="font-size: 45px;color: aqua" class="glyphicon glyphicon-plane" aria-hidden="true"></span></p>
                                        <p style="font-size: 17px;text-align: center">[dni]</p>
                                        <p style="text-align: center; font-size: 30px"><%--${vacationLimit}--%>${vacationLeave}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="panel panel-login">
                                    <div class="panel-body">
                                        <p style="font-size: 17px;text-align: center">Urlop na żądanie</p>
                                        <p style="text-align: center"><span style="font-size: 45px;color: aqua" class="glyphicon glyphicon-bell" aria-hidden="true"></span></p>
                                        <p style="font-size: 17px;text-align: center">[dni]</p>
                                        <p style="text-align: center; font-size: 30px"><%--${vacationLimit}--%>${emergencyVacation}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</html>