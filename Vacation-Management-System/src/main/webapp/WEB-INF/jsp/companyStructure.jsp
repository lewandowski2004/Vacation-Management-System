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
    <link rel="stylesheet" href="/css/application.css">
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
    <div id="addEmployee" class="row">
        <c:if test="${not empty failedMessage}">
            <div class="col-xs-12">
                <div class="alert alert-danger alert-dismissible">
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
                            <h3 style="text-align: center">Struktura Firmy</h3>
                            <hr>
                            <div class="form-group row">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="register-submit"
                                               onclick="window.location.href='${pageContext.request.contextPath}/admin/addDepartment'"
                                               tabindex="4" class="form-control btn btn-success"
                                               value="Dodaj Dział"/>
                                    </div>
                                    <br/> <br/>

                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="register-submit"
                                               onclick="window.location.href='${pageContext.request.contextPath}/admin/addPosition'"
                                               tabindex="4" class="form-control btn btn-success"
                                               value="Dodaj Stanowisko"/>
                                    </div>
                                    <br/> <br/>

                                </div>
                            </div>
                            <hr>
                            <div class="col-lg-5">
                                <c:forEach var="departmentDto" items="${departmentDtoList}">
                                    <div>
                                        <div class="col-lg-8">
                                            <p style="font-weight: bold">${departmentDto.name}:</p>
                                        </div>
                                        <div class="col-lg-4">
                                            <a style=" padding-right: 20px"
                                               data-toggle="tooltip"
                                               title="Edycja działu">
                                               href="/admin/edit/department/${departmentDto.id}"
                                                <span style="color: #1b6d85" class="glyphicon glyphicon-edit"></span>
                                            </a>
                                             <a style=" color: red" href="/admin/delete/department/${departmentDto.id}"
                                                data-toggle="tooltip"
                                                title="Usunięcie działu"
                                                onclick="return confirm('Usunięcie działu spowoduje usunięcie wszystkich stanowisk w ' +
                                                 'danym dziale. Czy na pewno chcesz usunąć dział ?')">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                             </a>
                                        </div>
                                    </div>
                                    <hr style="width: 100%">
                                    <div style="margin-left:53px">
                                        <c:forEach var="positionDto" items="${departmentDto.positionsDto}">
                                            <div class="col-lg-7">
                                                <p>${positionDto.name}</p>
                                            </div>
                                            <div class="col-lg-5">
                                                <a style=" padding-right: 20px"
                                                   href="/admin/edit/position/${positionDto.id}"
                                                   data-toggle="tooltip"
                                                   title="Edycja stanowiska">
                                                    <span style="color: #1b6d85"
                                                          class="glyphicon glyphicon-edit"></span>
                                                </a>
                                                <a style=" color: red" href="/admin/delete/position/${positionDto.id}"
                                                   onclick="return confirm('Czy na pewno chcesz usunąć stanowisko ?')"
                                                   data-toggle="tooltip"
                                                   title="Usunięcie stanowiska">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        $('[data-toggle="tooltip"]').tooltip({
            selector: true,
            title: function() {
                return $(this).attr('title');
            }
        });
    });
</script>
</body>

</html>