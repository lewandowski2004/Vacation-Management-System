<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>
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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.css"/>
    <link rel="stylesheet" href="/css/loginForm.css">

</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>

<div class="container">
    <div class="col-md-12">
        <div class="row">
            <div class="col-xs-12">
                <table id="table_id" class="display">
                    <thead>
                    <tr>
                        <th style="width: 260px">Status</th>
                        <th>Pracownik</th>
                        <th>Data złożenia</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="applicationDto" items="${applicationList}">
                        <tr>
                            <th>
                                <c:choose>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 1 }">
                                        <span style="color: #666666" class="glyphicon glyphicon-ok"></span>&nbsp;
                                        ${applicationDto.applicationStatusDto.status}
                                    </c:when>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 2 }">
                                        <span style="color: chartreuse" class="glyphicon glyphicon-ok"></span>&nbsp;
                                        ${applicationDto.applicationStatusDto.status}
                                    </c:when>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 3 }">
                                        <span style="color: green" class="glyphicon glyphicon-ok"></span>&nbsp;
                                        ${applicationDto.applicationStatusDto.status}
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red" class="glyphicon glyphicon-remove"></span>&nbsp;
                                        ${applicationDto.applicationStatusDto.status}
                                    </c:otherwise>
                                </c:choose>
                            </th>
                            <th>${applicationDto.employeeDto.name} ${applicationDto.employeeDto.lastName}</th>
                            <th>${applicationDto.dateOfAddition}</th>
                            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                                <c:choose>
                                    <c:when test="${applicationDto.employeeDto.id != employeeDto.id}">
                                        <th style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/admin/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </th>
                                    </c:when>
                                    <c:otherwise>
                                        <th style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </th>
                                    </c:otherwise>
                                </c:choose>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_MANAGER')">
                                <c:choose>
                                    <c:when test="${applicationDto.employeeDto.id != employeeDto.id}">
                                        <th style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/manager/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </th>
                                    </c:when>
                                    <c:otherwise>
                                        <th style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </th>
                                    </c:otherwise>
                                </c:choose>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_EMPLOYEE')">
                                <th style="text-align: center">
                                    <button class="btn btn-info"
                                            onclick="window.location.href='${pageContext.request.contextPath}/application/${applicationDto.id}'">
                                        <span class="glyphicon glyphicon-eye-open"></span>
                                    </button>
                                </th>
                            </security:authorize>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<span id="errorSearch" style="color: red;"></span>
</div>

</body>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#table_id').DataTable();
    });
</script>


</html>