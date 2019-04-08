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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"/>
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
                            <h3 style="text-align: center">Historia urlopu </h3>
                            <hr>

                            <table style="width: 100%" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <thead>
                                <tr style="border-bottom: 1px solid #666666; height: 40px">
                                    <th style="width: 100px">Limit dni</th>
                                    <th style="width: 260px">Urlop wypoczynkowy [dni]</th>
                                    <th style="width: 200px">Urlop na żądanie [dni]</th>
                                    <th>Rok</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="vacationBalanceDto" items="${employeeDto.vacationBalancesDto}">
                                    <tr style="border-bottom: 1px solid #ececec; height: 40px">
                                        <td>${vacationBalanceDto.vacationLimit}</td>
                                        <td>${vacationBalanceDto.annualVacation}</td>
                                        <td>${vacationBalanceDto.emergencyVacation}</td>
                                        <td>${vacationBalanceDto.year}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </br>
                            <div class="form-group row">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="register-submit"
                                               onclick="window.location.href='${pageContext.request.contextPath}/'"
                                               tabindex="4" class="form-control btn btn-info"
                                               value="Cofnij"/>
                                    </div>
                                    <br/> <br/>
                                </div>
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
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
</body>

</html>