<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script  type="text/javascript">
        $(function() {
            // timeout alert
            var $alert = $('.alert');

            if($alert.length){
                setTimeout(function() {
                    $alert.fadeOut('slow');
                }, 3000)
            }
        });
    </script>
</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>
<div class="container">
    <div class="row">

        <c:if test="${not empty succesMessage}">
            <div class="col-xs-12">
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${succesMessage}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty failedMessage}">
            <div class="col-xs-12">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${failedMessage}
                </div>
            </div>
        </c:if>

        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Dodawanie Działu</h3>
                            <hr>
                            <p style="color: red">${blad}</p>
                            <form:form id="register-form" modelAttribute="departmentDto"
                                       action="addDepartmentAction" method="POST" role="form">
                                <div class="form-group">
                                    <label>Nazwa Działu</label>
                                    <form:input type="text" name="name" id="name" path="name"
                                                tabindex="2" class="form-control" value=""/>
                                    <form:errors path="name" cssClass="help-block" element="em"/>
                                </div>
                                <br/>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="action" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-success"
                                                   onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                                   value="Dodaj Dział"/>
                                        </div>
                                        <br/> <br/>
                                    </div>
                                </div>
                            </form:form>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="register-submit"
                                               tabindex="4" class="form-control btn btn-danger"
                                               onclick="window.location.href='${contextRoot}/admin/companyStructure'"
                                               value="Anuluj"/>
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
</body>

</html>