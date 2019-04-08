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
    <link rel="stylesheet" href="/css/application.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker-standalone.css">
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>

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
                        <h3 style="text-align: center">Dodawanie Wniosku</h3>
                        <hr>
                        <p style="color: red">${blad}</p>
                        <form:form id="register-form" modelAttribute="applicationDto"
                                   action="addApplicationAction" method="POST" role="form">
                            <div class="form-group">
                                <dl class="dl-horizontal">
                                    <dt>Imię:</dt>
                                    <dd>${employeeDto.name}</dd>
                                    <dt>Nazwisko:</dt>
                                    <dd>${employeeDto.lastName}</dd>
                                    <dt>Dział/Stanowisko:</dt>
                                    <dd>${employeeDto.departmentDto.name}/${employeeDto.positionDto.name}</dd>
                                </dl>
                            </div>
                            <hr>
                            <div class="form-group">
                                <label style="width: 100%" class="col-form-label"><p>Data urlopu</p></label>
                                <div class='col-lg-5'>
                                    <div class='input-group date' id='datetimepicker6'>
                                        <form:input style="height: 34px" type='text' class="form-control"
                                                    id="startOfVacation" path="startOfVacation"/>
                                        <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                    </div>
                                    <form:errors path="startOfVacation" cssClass="help-block" element="em"/>
                                </div>
                                <label class="col-form-label"><p>do</p></label>
                                <div class='col-lg-5'>

                                    <div class='input-group date' id='datetimepicker7'>
                                        <form:input style="height: 34px" type='text' class="form-control"
                                                    id="endOfVacation" path="endOfVacation"/>
                                        <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                    </div>
                                    <form:errors path="endOfVacation" cssClass="help-block" element="em"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-form-label"><p>Liczba dni</p></label>
                                <form:input type="text" name="zipCode" id="sumDay" path="vacationDays"
                                            tabindex="2" class="form-control" readonly="true"/>
                            </div>

                            <div class="form-group">
                                <label>Wybierz rodzaj Urlopu</label>
                                <form:select path="vacationTypeDto" class="form-control">
                                    <form:options items="${vacationTypeList}" itemLabel="type"
                                                  itemValue="id" />
                                </form:select>
                            </div>

                            <div class="form-group">
                                <label>Wybierz Zastępstwo</label>
                                <form:select path="replacement" class="form-control">
                                    <form:options items="${listOfEmployeesForReplacement}" itemLabel="lastName"
                                                  itemValue="lastName" />
                                </form:select>
                            </div>
                            <br/>
                            <div class="form-group">
                                <label>Komentarz</label>
                                <form:textarea cssStyle="border-radius: 4px;padding: 6px 12px;width: 100%; border: 1px solid #ccc;" path="description" name="description"  rows="5"></form:textarea>
                            </div>
                            <br/>
                            <input type="radio" name="vacationPlan" hidden  path="vacationPlan" value="false" checked/>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="action" id="register-submit"
                                               tabindex="4" class="form-control btn btn-success"
                                               onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                               value="Dodaj Wniosek"/>
                                    </div>
                                    <br/> <br/>

                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%--<div class="container">
    <div class="row">
        <c:if test="${not empty message}">
            <div class="col-xs-12">
                <div class="alert alert-success alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${message}
                </div>
            </div>
        </c:if>
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <br/>
                            <h3 style="text-align: center">Dodawanie Wniosku</h3>
                            <hr>
                            <p style="color: red">${blad}</p>
                            <form:form id="register-form" modelAttribute="applicationDto"
                                       action="addApplicationAction" method="POST" role="form">
                                <div class="form-group">
                                    <dl class="dl-horizontal">
                                        <dt>Imię:</dt>
                                        <dd>${employeeDto.name}</dd>
                                        <dt>Nazwisko:</dt>
                                        <dd>${employeeDto.lastName}</dd>
                                        <dt>Dział/Stanowisko:</dt>
                                        <dd>${employeeDto.departmentDto.name}/${employeeDto.positionDto.name}</dd>
                                    </dl>
                                </div>
                                <hr>
                                <div class="form-group">
                                    <label style="width: 100%" class="col-form-label"><p>Data urlopu</p></label>
                                    <div class='col-lg-5'>
                                        <div class='input-group date' id='datetimepicker6'>
                                            <form:input style="height: 34px" type='text' class="form-control"
                                                        id="startOfVacation" path="startOfVacation"/>
                                            <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                        </div>
                                        <form:errors path="startOfVacation" cssClass="help-block" element="em"/>
                                    </div>
                                    <label class="col-form-label"><p>do</p></label>
                                    <div class='col-lg-5'>

                                        <div class='input-group date' id='datetimepicker7'>
                                            <form:input style="height: 34px" type='text' class="form-control"
                                                        id="endOfVacation" path="endOfVacation"/>
                                            <span class="input-group-addon">
                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                        </div>
                                        <form:errors path="endOfVacation" cssClass="help-block" element="em"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-form-label"><p>Liczba dni</p></label>
                                    <form:input type="text" name="vacationDays" id="sumDay" path="vacationDays"
                                                tabindex="2" class="form-control" readonly="true"/>
                                </div>
                                <div class="form-group">
                                    <label>Wybierz rodzaj Urlopu</label>
                                    <form:select id="vacationTypeDto" class="form-control" path="vacationTypeDto"
                                                 items="${vacationTypeMap}"/>

                                </div>
                                <br/>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="action" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-success"
                                                   onclick="return confirm('Czy na pewno chcesz dodać ?')"
                                                   value="Dodaj Wniosek"/>
                                        </div>
                                        <br/> <br/>

                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>--%>
<script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script type='text/javascript'
        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>


<script type="text/javascript">
    $(function () {
        $('#datetimepicker6').datetimepicker({
            format: 'YYYY/MM/DD',
            locale: 'pl',
            daysOfWeekDisabled: [0, 6]
        });

        $('#datetimepicker7').datetimepicker({
            format: 'YYYY/MM/DD',
            locale: 'pl',
            daysOfWeekDisabled: [0, 6],
            useCurrent: false //Important! See issue #1075

        });
        $("#datetimepicker6").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });

    });

</script>
<script>
    $(document).ready(function () {
        $("#datetimepicker7").on("dp.change", function () {

            var d1 = $('#startOfVacation').val();
            var d2 = $('#endOfVacation').val();

            var elem = document.getElementById("sumDay");
            elem.value = workingDaysBetweenDates(d1, d2);
        });
    });

    function workingDaysBetweenDates(d0, d1) {
        var startDate = parseDate(d0);
        var endDate = parseDate(d1);
        // populate the holidays array with all required dates without first taking care of what day of the week they happen
        var holidays = ['2018-12-09', '2018-12-10', '2018-12-24', '2018-12-31'];
        // Validate input
        if (endDate < startDate)
            return 0;

        var z = 0; // number of days to substract at the very end
        for (i = 0; i < holidays.length; i++) {
            var cand = parseDate(holidays[i]);
            var candDay = cand.getDay();

            if (cand >= startDate && cand <= endDate && candDay != 0 && candDay != 6) {
                // we'll only substract the date if it is between the start or end dates AND it isn't already a saturday or sunday
                z++;
            }

        }
        // Calculate days between dates
        var millisecondsPerDay = 86400 * 1000; // Day in milliseconds
        startDate.setHours(0, 0, 0, 1);  // Start just after midnight
        endDate.setHours(23, 59, 59, 999);  // End just before midnight
        var diff = endDate - startDate;  // Milliseconds between datetime objects
        var days = Math.ceil(diff / millisecondsPerDay);

        // Subtract two weekend days for every week in between
        var weeks = Math.floor(days / 7);
        days = days - (weeks * 2);

        // Handle special cases
        var startDay = startDate.getDay();
        var endDay = endDate.getDay();

        // Remove weekend not previously removed.
        if (startDay - endDay > 1)
            days = days - 2;

        // Remove start day if span starts on Sunday but ends before Saturday
        if (startDay == 0 && endDay != 6)
            days = days - 1

        // Remove end day if span ends on Saturday but starts after Sunday
        if (endDay == 6 && startDay != 0)
            days = days - 1

        // substract the holiday dates from the original calculation and return to the DOM
        return days - z;
    }

    function parseDate(input) {
        // Transform date from text to date
        var parts = input.match(/(\d+)/g);
        // new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
        return new Date(parts[0], parts[1] - 1, parts[2]); // months are 0-based
    }
</script>
</body>

</html>