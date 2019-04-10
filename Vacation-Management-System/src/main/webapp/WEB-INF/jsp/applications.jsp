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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/loginForm.css">

</head>
<body>
<%@include file="/WEB-INF/include/menu.jsp" %>

<div class="container">
    <div class="col-md-12">
        <div class="row">
            <div class="col-xs-12">
                <table id="table_id" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th></th>
                        <th style="width: 200px">Status</th>
                        <th>Pracownik</th>
                        <th>Dział</th>
                        <th>Typ wniosku</th>
                        <th>Data złożenia</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="applicationDto" items="${applicationList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 1 }">
                                        <span style="color: orange; font-size: 21px" class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;
                                    </c:when>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 2 }">
                                        <span style="color: chartreuse; font-size: 21px" class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;
                                    </c:when>
                                    <c:when test="${applicationDto.applicationStatusDto.id == 3 }">
                                        <span style="color: green; font-size: 21px" class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red; font-size: 21px" class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${applicationDto.applicationStatusDto.status}
                            </td>
                            <td>${applicationDto.employeeDto.name} ${applicationDto.employeeDto.lastName}</td>
                            <td>${applicationDto.employeeDto.departmentDto.name}</td>
                            <td>${applicationDto.vacationTypeDto.type}</td>
                            <td>${applicationDto.dateOfAddition}</td>
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
                                        <td style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/manager/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="text-align: center">
                                            <button class="btn btn-info"
                                                    onclick="window.location.href='${pageContext.request.contextPath}/application/${applicationDto.id}'">
                                                <span class="glyphicon glyphicon-eye-open"></span>
                                            </button>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </security:authorize>
                            <security:authorize access="hasAuthority('ROLE_EMPLOYEE')">
                                <td style="text-align: center">
                                    <button class="btn btn-info"
                                            onclick="window.location.href='${pageContext.request.contextPath}/application/${applicationDto.id}'">
                                        <span class="glyphicon glyphicon-eye-open"></span>
                                    </button>
                                </td>
                            </security:authorize>

                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th>Status</th>
                        <th>Pracownik</th>
                        <th>Dział</th>
                        <th>Typ wniosku</th>
                        <th>Data złożenia</th>
                        <th></th>
                    </tr>
                    </tfoot>
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
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>

<script>
    $(document).ready(function() {
        $('#table_id').DataTable( {
            initComplete: function () {
                this.api().columns([1, 2, 3 ,4]).every( function () {
                    var column = this;
                    var select = $('<select class="form-control"><option value="">Wszystkie</option></select>')
                        .appendTo( $(column.footer()).empty() )
                        .on( 'change', function () {
                            var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );

                            column
                                .search( val ? '^'+val+'$' : '', true, false )
                                .draw();
                        } );

                    column.data().unique().sort().each( function ( d, j ) {
                        select.append( '<option  value="'+d+'">'+d+'</option>' )
                    } );
                } );
            }
        } );
    } );
</script>



</html>