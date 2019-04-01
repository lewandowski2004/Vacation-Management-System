<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/css/loginForm.css">
    <link rel="stylesheet" href="/css/application.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker-standalone.css">
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
    <div id="addEmployee" class="row">

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

        <div class="col-md-8 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br />
                            <h3 style="text-align: center">Dodawanie Użytkownika</h3>
                            <hr>
                            <p style="color: red">${blad}</p>
                            <form:form id="add-form" name="employeeEditForm" modelAttribute="employeeDto"
                                       action="/admin/update/employee/${employeeDto.id}" method="POST" role="form">
                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Imie</p></label>
                                    <div class="col-lg-4">
                                        <form:input type="text" name="name" id="name" path="name"
                                                    tabindex="2" class="form-control" placeholder="Imie" />
                                        <form:errors path="name" cssClass="help-block" element="em" />
                                    </div>
                                    <label class="col-lg-2 col-form-label"><p>Nazwisko</p></label>
                                    <div class="col-lg-4">
                                        <form:input type="text" name="lastName" id="lastName" path="lastName"
                                                    tabindex="2" class="form-control" placeholder="Nazwisko" />
                                        <form:errors path="lastName" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Email</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="email" id="email" path="email" readonly="true"
                                                    tabindex="2" class="form-control" placeholder="Email" />
                                        <form:errors path="email" cssClass="help-block" element="em" />
                                    </div>

                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>PESEL</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="pesel" id="pesel" path="pesel"
                                                    tabindex="2" class="form-control" placeholder="pesel" />
                                        <form:errors path="pesel" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Data</p></label>
                                    <div class="col-lg-10">
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker8'>
                                                <form:input type='text' class="form-control" name="addressLine1" id="addressLine1" path="dateOfBirth"
                                                            tabindex="2"  placeholder="${employee.dateOfBirth}"/>
                                                <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar">

                                                            </span>
                                                        </span>
                                            </div>
                                            <form:errors path="dateOfBirth" cssClass="help-block" element="em" />
                                        </div>
                                    </div>
                                    <script type="text/javascript">
                                        $(function () {
                                            $('#datetimepicker8').datetimepicker({
                                                format: 'DD/MM/YYYY',
                                                viewMode: 'years',
                                                locale: 'pl'
                                            });
                                        });
                                    </script>

                                </div>


                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Adres 1</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="addressLine1" id="addressLine1" path="addressLine1"
                                                    tabindex="2" class="form-control" placeholder="adres 1" />
                                        <form:errors path="addressLine1" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Adres 2</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="addressLine2" id="addressLine2" path="addressLine2"
                                                    tabindex="2" class="form-control" placeholder="adres 2" />
                                        <form:errors path="addressLine2" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Miasto</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="city" id="city" path="city"
                                                    tabindex="2" class="form-control" placeholder="miasto" />
                                        <form:errors path="city" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Kod pocztowy</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="zipCode" id="zipCode" path="zipCode"
                                                    tabindex="2" class="form-control" placeholder="kod pocztowy" />
                                        <form:errors path="zipCode" cssClass="help-block" element="em" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Nr. telefonu</p></label>
                                    <div class="col-lg-10">
                                        <form:input type="text" name="phoneNumber" id="phoneNumber" path="phoneNumber"
                                                    tabindex="2" class="form-control" placeholder="numer telefonu" />
                                        <form:errors path="phoneNumber" cssClass="help-block" element="em" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Rola</p></label>
                                    <div class="col-lg-10">
                                        <form:select id="roleSelect" class="form-control"  path="nrRoli" items="${roleMap}"></form:select>
                                        <form:errors path="nrRoli" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Dział</p></label>
                                    <div class="col-lg-10">
                                        <form:select id="departmentSelect" class="form-control"  path="nrDepartment">
                                                <form:options items="${departmentList}" itemLabel="name"
                                                              itemValue="id" />
                                        </form:select>
                                        <form:errors path="nrDepartment" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Stanowisko</p></label>
                                    <div class="col-lg-10">

                                        <form:select id="positionSelect" class="form-control"  path="positionDto" >
                                            <form:option value="${employeeDto.positionDto.id}">${employeeDto.positionDto.name}</form:option>
                                        </form:select>
                                        <form:errors path="positionDto" cssClass="help-block" element="em" />
                                    </div>
                                </div>


                                <%--
                                <hr>
                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Dział</p></label>
                                    <div class="col-lg-10">
                                        <form:select id="departmentSelect" class="form-control"  path="department" >
                                            <form:option value="${department.id}">Wybierz dział</form:option>
                                            <form:options items="${departmentMap}"></form:options>
                                        </form:select>
                                        <form:errors path="department" cssClass="help-block" element="em" />
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-lg-2 col-form-label"><p>Stanowisko</p></label>
                                    <div class="col-lg-10">
                                        <form:select id="positionSelect" class="form-control"  path="position" ></form:select>
                                        <form:errors path="position" cssClass="help-block" element="em" />
                                    </div>
                                </div>--%>
                                <br />
                                <div class="form-group row">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="action" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-success"
                                                   onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                                   value="Dodaj Użytkownika" />
                                        </div>
                                        <br /> <br />

                                    </div>
                                </div>
                            </form:form>
                            <div class="form-group row">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="anuluj-submit"
                                               tabindex="4" class="form-control btn btn-danger"
                                               onclick="window.location.href='${pageContext.request.contextPath}/'"
                                               value="Anuluj" />
                                    </div>
                                    <br /> <br />

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">
    $(document).ready(
        function() {

            $('#departmentSelect').on("change", function () {
                var departmentId = $("#departmentSelect option:selected").val();
                $.ajax({
                    type: "GET",
                    url: "/admin/getPositiontMapByDepartmentId/"+departmentId,
                    success: function (result) {
                        var r = JSON.parse(result);
                        var position = "";
                        for(var i = 0; i<r.length; i++){
                            position+= '<option value="' + r[i].key + '">' + r[i].value +'</option>';
                        }
                        $('#positionSelect').html(position);
                    }
                });
            });
        });

</script>

<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

</body>

</html>