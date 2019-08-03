<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<nav class="navbar navbar-default" style="top: 0;width: 100%;">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Brand</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse"
             id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav">
                <li><a href="${contextRoot}/">Strona Główna</a></li>
                <li class="dropdown"><a href="#" class="dropdown-toggle"
                                        data-toggle="dropdown" role="button" aria-haspopup="true"
                                        aria-expanded="false"> Dodaj<span class="caret"></span>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="${contextRoot}/addApplication">Wniosek urlopowy</a></li>
                        <li><a href="${contextRoot}/addVacationPlan">Zaplanuj urlop</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle"
                                        data-toggle="dropdown" role="button" aria-haspopup="true"
                                        aria-expanded="false"> Twoje wnioski<span class="caret"></span>
                </a>
                    <ul class="dropdown-menu">
                        <li><a href="${contextRoot}/applications">Twoje wnioski urlopowe</a></li>
                        <li><a href="${contextRoot}/vacationPlans">Zaplanowany urlop</a></li>
                    </ul>
                </li>
                <li><a href="${contextRoot}/vacationInformation">Historia urlopu</a></li>

                <security:authorize access="hasAuthority('ROLE_ADMIN')">
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false"> Panel Zarządzania<span class="caret"></span>
                    </a>
                        <ul class="dropdown-menu">
                            <li><a href="${contextRoot}/admin/companyStructure">Struktura firmy</a></li>
                            <li><a href="${contextRoot}/admin/addEmployee">Dodaj Użytkownika</a></li>
                            <li><a href="${contextRoot}/admin/vacationBalanceEmployees">Aktualizowanie rocznego urlopu</a></li>
                            <li><a href="${contextRoot}/admin/employees">Uzytkownicy</a></li>
                            <li><a href="${contextRoot}/admin/employeesVacationInfo">Bilans urlopowy pracowników</a></li>
                            <li><a href="${contextRoot}/admin/applications">Wnioski urlopowe</a></li>
                            <li><a href="${contextRoot}/admin/vacationPlans">Planowane urlopy</a></li>
                        </ul>
                    </li>
                </security:authorize>

                <security:authorize access="hasAuthority('ROLE_MANAGER')">
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false"> Panel Zarządzania<span class="caret"></span>
                    </a>
                        <ul class="dropdown-menu">
                            <li><a href="${contextRoot}/manager/applications">Wnioski urlopowe</a></li>
                            <li><a href="${contextRoot}/manager/vacationPlans">Planowane urlopy</a></li>
                        </ul>
                    </li>
                </security:authorize>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <security:authorize access="isAuthenticated()">
                    <li class="dropdown"><%--<a href="javascript:void(0)"
                                            class="dropdown-toggle" data-toggle="dropdown" id="dropdownMenu1"
                                            role="button" aria-haspopup="true" aria-expanded="false">
                        <images style="max-height:32px;max-width:32px" class="images-circle" src="${images}/user.png"
                             alt="zdjęcie" width="64">${userModel.login} <span class="caret"></span>
                    </a>--%>
                        <a href="#" class="dropdown-toggle"
                           data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">${employee.email}<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/profil">Profil</a></li>
                            <li><a href="/editPassword">Zmień hasło</a></li>
                            <li><a href="/logout">Wyloguj</a></li>

                        </ul>
                    </li>
                </security:authorize>


            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
