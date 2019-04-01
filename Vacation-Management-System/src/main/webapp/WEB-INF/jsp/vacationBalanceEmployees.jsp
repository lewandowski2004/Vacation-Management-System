<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/css/loginForm.css">
    <link rel="stylesheet" href="/css/application.css">
</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>
<div class="container">
    <div id="addEmployee" class="row">
        <div class="col-md-8 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Aktualizacja rocznego urlopu</h3>
                            <hr>
                            <div class="list-group">
                                <c:forEach var="employeeDto" items="${vacationBalanceDtoByYearList}">
                                    <a href="#" class="list-group-item list-group-item-success">
                                            ${employeeDto.name}
                                            ${employeeDto.lastName}
                                            ${employeeDto.name}
                                    </a>
                                </c:forEach>
                                <c:forEach var="employeeDto" items="${vacationBalanceDtoByYearIsNotInList}">
                                    <a href="/admin/addVacationBalance/employee/${employeeDto.id}" class="list-group-item list-group-item-danger">
                                        ${employeeDto.name}
                                        ${employeeDto.lastName}
                                        ${employeeDto.name}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>

</html>