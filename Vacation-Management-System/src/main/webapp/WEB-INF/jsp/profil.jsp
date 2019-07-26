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
                            <h3 style="text-align: center">Profil Użytkownika </br></br>
                                <c:out value=" ${employeeDto.name } ${employeeDto.lastName }"/></h3>
                            <hr>
                            <table>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Imie:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.name }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Nazwisko:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.lastName }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Email:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.email }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Data urodzenia:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.dateOfBirth }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        PESEL:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.pesel }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Nr. telefonu:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.phoneNumber }"/>
                                    </td>
                                </tr>
                            </table>
                            <hr>
                            <table>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Adres:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.addressLine1 }"/>
                                        <c:out value="${employeeDto.addressLine2 }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Miasto:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.city }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Kod pocztowy:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.zipCode }"/>
                                    </td>
                                </tr>
                            </table>
                            <hr>
                            <table>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Dział:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.departmentDto.name }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Stanowisko:
                                    </td>
                                    <td width="270" align="left">
                                        <c:out value="${employeeDto.positionDto.name }"/>
                                    </td>
                                </tr>
                                <tr style="height: 30px">
                                    <td width="130" align="left">
                                        Rola:
                                    </td>
                                    <td width="270" align="left">
                                        <c:choose>
                                            <c:when test="${employeeDto.nrRoli == 2 }">
                                                <p>Admin</p>
                                            </c:when>
                                            <c:when test="${employeeDto.nrRoli == 4 }">
                                                <p>Kierownik</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p>Pracownik</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                            </br>
                            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                                <div class="form-group row">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="action" id="register-submit"
                                                   onclick="window.location.href='${pageContext.request.contextPath}/admin/edit/employee/${employeeDto.id}'"
                                                   tabindex="4" class="form-control btn btn-info"
                                                   value="Edytuj Użytkownika"/>
                                        </div>
                                        <br/> <br/>
                                    </div>
                                </div>
                            </security:authorize>
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