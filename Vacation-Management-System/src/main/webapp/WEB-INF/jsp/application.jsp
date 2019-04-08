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
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/css/loginForm.css">
</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>
<div class="container">
    <div id="addEmployee" class="row">
        <c:if test="${not empty succesMessage}">
            <div class="col-xs-12">
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${succesMessage}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty infoMessage}">
            <div class="col-xs-12">
                <div class="alert alert-info alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${failedMessage}
                </div>
            </div>
        </c:if>
        <div class="col-md-8 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Wniosek Urlopowy</h3>
                            <hr>
                            <p style="color: red">${blad}</p>
                            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                                <form:form id="register-form" modelAttribute="applicationDto"
                                           action="/admin/changeStatus/application/${applicationDto.id}" method="POST"
                                           role="form">
                                    <dl class="dl-horizontal">
                                        <dt>Imię:</dt>
                                        <dd>${applicationDto.employeeDto.name}</dd>
                                        <dt>Nazwisko:</dt>
                                        <dd>${applicationDto.employeeDto.lastName}</dd>
                                        <dt>Zastępstwo:</dt>
                                        <dd>${applicationDto.replacement}</dd>
                                        <dt>Data wystawienia:</dt>
                                        <dd>${applicationDto.dateOfAddition}</dd>
                                        <dt>Data:</dt>
                                        <dd>${applicationDto.startOfVacation} do ${applicationDto.endOfVacation}</dd>
                                        <dt>Ilość dni:</dt>
                                        <dd>${applicationDto.vacationDays}</dd>
                                        <dt>Rodzaj Wniosku:</dt>
                                        <dd>${applicationDto.vacationTypeDto.type}</dd>
                                        <dt>Komentarz:</dt>
                                        <dd>${applicationDto.description}</dd>
                                    </dl>
                                    <hr>
                                    <c:choose>
                                        <c:when test="${applicationDto.vacationTypeDto.id != 2}">
                                            <c:choose>
                                                <c:when test="${applicationDto.employeeDto.id != employeeDto.id}">
                                                    <div class="form-group">
                                                        <label>Status Wniosku</label>
                                                        <form:select id="inputState" class="form-control"
                                                                     path="nrStatus"
                                                                     items="${applicationStatusMapAdmin}"/>
                                                            <%-- <form:errors path="applicationStatusDto" cssClass="help-block"
                                                                          element="em"/>--%>
                                                    </div>

                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-sm-6 col-sm-offset-3">
                                                                <input type="submit" name="action" id="register-submit"
                                                                       tabindex="4" class="form-control btn btn-success"
                                                                       onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                                                       value="Zmień Status"/>
                                                            </div>
                                                            <br/> <br/>
                                                        </div>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                </form:form>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_MANAGER')">
                                <form:form id="register-form" modelAttribute="applicationDto"
                                           action="/manager/changeStatus/application/${applicationDto.id}" method="POST"
                                           role="form">
                                    <dl class="dl-horizontal">
                                        <dt>Imię:</dt>
                                        <dd>${applicationDto.employeeDto.name}</dd>
                                        <dt>Nazwisko:</dt>
                                        <dd>${applicationDto.employeeDto.lastName}</dd>
                                        <dt>Zastępstwo:</dt>
                                        <dd>${applicationDto.replacement}</dd>
                                        <dt>Data wystawienia:</dt>
                                        <dd>${applicationDto.dateOfAddition}</dd>
                                        <dt>Data:</dt>
                                        <dd>${applicationDto.startOfVacation} do ${applicationDto.endOfVacation}</dd>
                                        <dt>Ilość dni:</dt>
                                        <dd>${applicationDto.vacationDays}</dd>
                                        <dt>Rodzaj Wniosku:</dt>
                                        <dd>${applicationDto.vacationTypeDto.type}</dd>
                                        <dt>Komentarz:</dt>
                                        <dd>${applicationDto.description}</dd>
                                    </dl>
                                    <hr>

                                    <%--<c:choose>
                                        <c:when test="${application.applicationStatus.id != 3} ">--%>
                                    <c:choose>
                                        <c:when test="${applicationDto.vacationTypeDto.id != 2}">
                                            <c:choose>
                                                <c:when test="${applicationDto.employeeDto.id != employeeDto.id}">

                                                    <div class="form-group">
                                                        <label>Status Wniosku</label>
                                                        <form:select id="inputState" class="form-control"
                                                                     path="nrStatus"
                                                                     items="${applicationStatusMapManager}"/>
                                                            <%--<form:errors path="applicationStatus" cssClass="help-block"
                                                                         element="em"/>--%>
                                                    </div>

                                                    <div class="form-group">
                                                        <div class="row">
                                                            <div class="col-sm-6 col-sm-offset-3">
                                                                <input type="submit" name="action" id="register-submit"
                                                                       tabindex="4" class="form-control btn btn-success"
                                                                       onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                                                       value="Zmień Status"/>
                                                            </div>
                                                            <br/> <br/>
                                                        </div>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                    <%-- </c:when>
                                 </c:choose>--%>
                                </form:form>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_EMPLOYEE')">
                                <dl class="dl-horizontal">
                                    <dt>Imię:</dt>
                                    <dd>${applicationDto.employeeDto.name}</dd>
                                    <dt>Nazwisko:</dt>
                                    <dd>${applicationDto.employeeDto.lastName}</dd>
                                    <dt>Zastępstwo:</dt>
                                    <dd>${applicationDto.replacement}</dd>
                                    <dt>Data wystawienia:</dt>
                                    <dd>${applicationDto.dateOfAddition}</dd>
                                    <dt>Data:</dt>
                                    <dd>${applicationDto.startOfVacation} do ${applicationDto.endOfVacation}</dd>
                                    <dt>Ilość dni:</dt>
                                    <dd>${applicationDto.vacationDays}</dd>
                                    <dt>Rodzaj Wniosku:</dt>
                                    <dd>${applicationDto.vacationTypeDto.type}</dd>
                                    <dt>Komentarz:</dt>
                                    <dd>${applicationDto.description}</dd>
                                </dl>
                                <hr>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                                <c:choose>
                                    <c:when test="${applicationDto.employeeDto.id != employeeDto.id}">
                                        <c:choose>
                                            <c:when test="${applicationDto.applicationStatusDto.id != 2}">
                                                <c:choose>
                                                    <c:when test="${applicationDto.applicationStatusDto.id != 3}">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-sm-6 col-sm-offset-3">
                                                                    <input type="submit" name="action"
                                                                           id="delete-submit"
                                                                           tabindex="4"
                                                                           class="form-control btn btn-danger"
                                                                           onclick="window.location.href='${pageContext.request.contextPath}/admin/delete/application/${applicationDto.id }'"
                                                                           value="Usuń"/>
                                                                </div>
                                                                <br/> <br/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="action" id="delete-submit"
                                                           tabindex="4" class="form-control btn btn-danger"
                                                           onclick="window.location.href='${pageContext.request.contextPath}/delete/application/${applicationDto.id }'"
                                                           value="Usuń"/>
                                                </div>
                                                <br/> <br/>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_EMPLOYEE') or hasAuthority('ROLE_MANAGER')">
                                <c:choose>
                                    <c:when test="${applicationDto.employeeDto.id == employeeDto.id}">
                                        <c:choose>
                                            <c:when test="${applicationDto.applicationStatusDto.id != 2}">
                                                <c:choose>
                                                    <c:when test="${applicationDto.applicationStatusDto.id != 3}">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-sm-6 col-sm-offset-3">
                                                                    <input type="submit" name="action"
                                                                           id="delete-submit"
                                                                           tabindex="4"
                                                                           class="form-control btn btn-danger"
                                                                           onclick="window.location.href='${pageContext.request.contextPath}/delete/application/${applicationDto.id }'"
                                                                           value="Usuń"/>
                                                                </div>
                                                                <br/> <br/>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </security:authorize>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>

</html>