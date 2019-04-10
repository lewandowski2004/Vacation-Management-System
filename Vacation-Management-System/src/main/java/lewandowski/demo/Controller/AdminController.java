package lewandowski.demo.Controller;

import com.google.gson.Gson;
import lewandowski.demo.DTO.*;
import lewandowski.demo.Model.*;
import lewandowski.demo.Service.*;
import lewandowski.demo.Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationStatusService applicationStatusService;

    @Autowired
    private VacationBalanceService vacationBalanceService;

    @Autowired
    private EmployeeVacationBalanceService employeeVacationBalanceService;

    @Autowired
    private AppComponentSelectMap appComponentSelectMap;

    @Autowired
    private DepartmentEditor departmentEditor;

    @Autowired
    private PositionEditor positionEditor;

    @Autowired
    private EmailSender emailSender;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DepartmentDto.class, this.departmentEditor);
        binder.registerCustomEditor(PositionDto.class, this.positionEditor);
    }
    @InitBinder({"employeeDto", "employeeWithVacationBalanceDto", "vacationBalanceDto"})
    public void customizeBinding (WebDataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(Date.class, "dateOfBirth",
                new CustomDateEditor(dateFormatter, true));
    }

    /**
    * @return page with the employee adding form.
    * */
    @GetMapping(value = "/admin/addEmployee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String EmployeeForm(EmployeeWithVacationBalanceDto employeeWithVacationBalanceDto, Model model, BindingResult result,
                               @RequestParam(name = "success", required = false) String success) {
        model.addAttribute("employeeWithVacationBalanceDto", employeeWithVacationBalanceDto);
        model.addAttribute("departmentMap", appComponentSelectMap.prepareDepartmentDtoMap());
        if (success != null) {
            if (success.equals("addEmployee")) {
                model.addAttribute("succesMessage", "Pracownik dodany !");
            }
        }
        return "addEmployee";
    }

    /**
     * @param departmentId Department ID.
     * @return list positions in department with param, date is in JSON format.
     * */
    @GetMapping(value = "/admin/getPositiontMapByDepartmentId/{departmentId}")
    @ResponseBody
    public String getPositionMapByDepartmentId(Model model,
                                                @PathVariable("departmentId") int departmentId) {
        Gson gson = new Gson();
        Map<Integer, String> positiontMapByDepartmentId = appComponentSelectMap.preparePositionDtoMapByDepartmentId(departmentId);
        return gson.toJson(positiontMapByDepartmentId.entrySet().toArray());
    }

    /**
     * The method is responsible for adding an employee.
     * @return success = after all conditions have been met, an employee is added,
     *         error = page with the employee adding form.
     * */
    @PostMapping(value = "/admin/addEmployeeAction")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addEmployee(@Valid EmployeeWithVacationBalanceDto employeeWithVacationBalanceDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employeeWithVacationBalanceDto", employeeWithVacationBalanceDto);
            model.addAttribute("departmentMap", appComponentSelectMap.prepareDepartmentDtoMap());
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "addEmployee";
        }
        if (employeeService.emailIsUnique(employeeWithVacationBalanceDto.getEmail()) == false) {
            model.addAttribute("employeeWithVacationBalanceDto", employeeWithVacationBalanceDto);
            model.addAttribute("failedMessage", "Taki login juz istnieje !!!");
            model.addAttribute("departmentMap", appComponentSelectMap.prepareDepartmentDtoMap());
            return "addEmployee";
        }
        if (employeeWithVacationBalanceDto.getPassword().equals(employeeWithVacationBalanceDto.getConfirmPassword()) == false) {
            model.addAttribute("employeeWithVacationBalanceDto", employeeWithVacationBalanceDto);
            model.addAttribute("failedMessage", "Hasła muszą być takie same !!!");
            model.addAttribute("departmentMap", appComponentSelectMap.prepareDepartmentDtoMap());
            return "addEmployee";
        }
        employeeVacationBalanceService.saveEmployeeVacationBalanceDto(employeeWithVacationBalanceDto);
        return "redirect:/admin/addEmployee?success=addEmployee";
    }

    /**
     * Method for removing an employee.
     * @param id Employee ID.
     * @return employee removed / page with list of employees.
     * */
    @PostMapping(value = "/admin/delete/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable("id") UUID id) {
        applicationService.deleteApplicationByEmployeeId(id);
        vacationBalanceService.deleteVacationBalanceByEmployeeId(id);
        employeeService.deleteEmployeeById(id);
        return "redirect:/admin/employees";
    }

    /**
     * Method displays the employee form with the option of editing fields.
     * @param id Employee ID.
     * @return page with the employee editing form.
     * */
    @GetMapping(value = "/admin/edit/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editEmployeeForm(@PathVariable("id") UUID id, Model model) {
        EmployeeDto employeeDto = employeeService.findById(id);

        int rola = employeeDto.getRolesDto().iterator().next().getId();
        employeeDto.setNrRoli(rola);
        int nrDepartment = employeeDto.getDepartmentDto().getId();
        employeeDto.setNrDepartment(nrDepartment);

        model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
        model.addAttribute("roleMap", appComponentSelectMap.prepareRoleMap());
        model.addAttribute("employeeDto", employeeDto);
        return "editprofil";
    }

    /**
     * Method for editing an employee.
     * @param id Employee ID.
     * @return  success = employee is edited / Page with list of employees,
     *          error = page with the employee editing form .
     * */
    @PostMapping(value = "/admin/update/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editEmployeeAction(@Valid @PathVariable("id") UUID id, EmployeeDto employeeDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
            model.addAttribute("roleMap", appComponentSelectMap.prepareRoleMap());
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "editprofil";
        }
        employeeService.updateEmployeeProfile(employeeDto.getName(), employeeDto.getLastName(), employeeDto.getEmail(),
                employeeDto.getPesel(), employeeDto.getDateOfBirth(), employeeDto.getAddressLine1(), employeeDto.getAddressLine2(),
                employeeDto.getCity(), employeeDto.getZipCode(), employeeDto.getPhoneNumber(), id);
        employeeService.updateEmployeeRole(id, employeeDto.getNrRoli());
        employeeService.updateDepartmentAndPositionEmployee(employeeDto.getNrDepartment(), employeeDto.getPositionDto().getId(), id);
        return "redirect:/admin/employees";
    }

    /**
     * Method gets employee data.
     * @param id Employee ID.
     * @return page with employee data.
     * */
    @GetMapping(value = "/admin/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEmployee(@PathVariable UUID id, Model model) {
        EmployeeDto employeeDto = employeeService.findById(id);
        int nrRoli = employeeDto.getRolesDto().iterator().next().getId();
        employeeDto.setNrRoli(nrRoli);
        model.addAttribute("employeeDto", employeeDto);
        return "profil";
    }

    /**
     * Method displays the form of adding vacation balance.
     * @param id Employee ID.
     * @return page with the vacation balance adding form.
     * */
    @GetMapping(value = "/admin/addVacationBalance/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String vacationBalanceForm(@PathVariable UUID id, Model model,
                                      @RequestParam(name = "success", required = false) String success) {
        EmployeeDto employeeDto = employeeService.findById(id);
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("vacationBalanceDto", new VacationBalanceDto());
        if (success != null) {
            if (success.equals("addVacationBalance")) {
                model.addAttribute("succesMessage", "Urlop Dodany!");
            }
        }
        return "addVacationBalance";
    }

    /**
     * Method displays the form of adding vacation balance.
     * @param id Employee ID.
     * @return success = adding employee's vacation balance,
     *         error = page with the vacation balance adding form.
     * */
    @PostMapping(value = "/admin/addVacationBalanceAction/employee/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addVacationBalance(@Valid VacationBalanceDto vacationBalanceDto, BindingResult result, Model model,
                                     @PathVariable UUID id) throws ParseException {
        EmployeeDto employeeDto = employeeService.findById(id);
        vacationBalanceDto.setYear(appComponentSelectMap.DateFormat(new Date()));
        vacationBalanceDto.setEmployeeDto(employeeDto);
        /*if (result.hasErrors()) {
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "addVacationBalance";
        }*/
        vacationBalanceService.saveVacationBalanceDto(vacationBalanceDto);
        return "redirect:/admin/addVacationBalance/employee/"+id+"?success=addVacationBalance";
    }

    /**
     * Method gets data with a list of employees.
     * @return page with list of employees.
     * */
    @GetMapping(value = "/admin/employees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllEmployees(Model model) {
        model.addAttribute("employeeDtoList", employeeService.findAllEmployeesDto());
        return "employees";
    }

    /**
     * Method gets list of employees with update vacation balance or no.
     * @return page with list of users with defined vacation and undefined.
     * */
    @GetMapping(value = "/admin/vacationBalanceEmployees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllEmployeesWithVacationPlan(Model model) throws ParseException {
       model.addAttribute("vacationBalanceDtoByYearList",
               employeeService.findAllEmployeesDtoByVacationBalancesWhereYearIs(appComponentSelectMap.DateFormat(new Date())));
        model.addAttribute("vacationBalanceDtoByYearIsNotInList",
                employeeService.findAllEmployeesDtoByVacationBalancesWhereYearIsNot(appComponentSelectMap.DateFormat(new Date())));
        return "vacationBalanceEmployees";
    }

    /**
     * Method gets data with a list of applications.
     * @return page with list of applications.
     * */
    @GetMapping(value = "/admin/applications")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllApplications(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("applicationList", applicationService.findAllApplicationsDtoAdmin(employeeDto.getId()));
        return "applications";
    }

    /**
     * Method gets data with a list of applications for planned vacation.
     * @return page with list of applications for planned vacation.
     * */
    @GetMapping(value = "/admin/vacationPlans")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllvacationPlans(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("applicationList", applicationService.findAllVacationPlansDtoAdmin(employeeDto.getId()));
        return "applications";
    }

    /**
     * Method gets application data.
     * @param id Application ID.
     * @return page with application data.
     * */
    @GetMapping(value = "/admin/application/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editStatusApplicationForm(@PathVariable("id") UUID id, Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);

        ApplicationDto applicationDto = applicationService.findApplicationDtoById(id);
        int status = applicationDto.getApplicationStatusDto().getId();
        applicationDto.setNrStatus(status);

        model.addAttribute("applicationStatusMapAdmin", appComponentSelectMap.prepareStatusMapAdmin());
        model.addAttribute("applicationDto", applicationDto);
        model.addAttribute("employeeDto", employeeDto);
        return "application";
    }

    @PostMapping(value = "/admin/changeStatus/application/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeApplicationStatus(@PathVariable("id") UUID id, ApplicationDto application, Model model, BindingResult result,
                                             @RequestParam(name = "nrStatus") Integer nrStatus) throws ParseException {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employee = employeeService.findByEmail(username);

        ApplicationDto applicationCurrent = applicationService.findApplicationDtoById(id);

        Date date  = appComponentSelectMap.returnDateByFormat("yyyy", new Date());

        Map<Integer, String> applicationStatusMapAdmin = appComponentSelectMap.prepareStatusMapAdmin();
        int status = applicationCurrent.getApplicationStatusDto().getId();
        applicationCurrent.setNrStatus(status);

        if (result.hasErrors()) {
            model.addAttribute("applicationStatusMapAdmin", applicationStatusMapAdmin);
            model.addAttribute("application", application);
            model.addAttribute("employee", employee);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "application";
            /*Sprawdzenie czy wniosek juz nie posiada wybranego statusu przez użytkownika, jeśli tak zwraca komunikat*/
        }
        if (applicationCurrent.getApplicationStatusDto().getId() != application.getNrStatus()) {
            /*Sprawdzenie czy statusem wniosku nie jest "1 = Oczekujący" ze wzgledu na prawidłowe operacje na urlopie użytkownika*/
            if (applicationCurrent.getApplicationStatusDto().getId() == 2 || applicationCurrent.getApplicationStatusDto().getId() == 3 || applicationCurrent.getApplicationStatusDto().getId() == 5) {
                /*Sprawdzenie jaki status wniosku został wybrany przez Administratora*/
                if (application.getNrStatus() == 4 || application.getNrStatus() == 5) {
                    /*Sprawdzenie jaki typ wniosku posiada dany wniosek "1 = wniosek o urlop wypoczynkowy"*/
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation = vacationLeaveBalance + vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/admin/applications";
                    }
                } else if (application.getNrStatus() == 3) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation = vacationLeaveBalance - vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance - vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/admin/applications";
                    }
                } else {
                    applicationService.updateStatusApplication(nrStatus, id);
                    return "redirect:/admin/applications";
                }
            } else  if (applicationCurrent.getApplicationStatusDto().getId() == 4) {
                if (application.getNrStatus() == 4 || application.getNrStatus() == 5) {
                    /*Sprawdzenie jaki typ wniosku posiada dany wniosek "1 = wniosek o urlop wypoczynkowy"*/
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {

                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/admin/applications";
                    }
                } else if (application.getNrStatus() == 3) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation = vacationLeaveBalance - vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance - vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/admin/applications";
                    }
                } else {
                    applicationService.updateStatusApplication(nrStatus, id);
                    return "redirect:/admin/applications";
                }
            } else {
                if (application.getNrStatus() == 4 || application.getNrStatus() == 5) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance;
                        vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation = vacationLeaveBalance + vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/admin/applications";
                    }
                } else if (application.getNrStatus() == 3) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        model.addAttribute("application", new Application());
                        return "redirect:/admin/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                                "Status Twojej aplikacji został zmieniony",
                                "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                        return "redirect:/admin/applications";
                    }
                } else {
                    applicationService.updateStatusApplication(nrStatus, id);
                    emailSender.sendMessage(applicationCurrent.getEmployeeDto().getEmail(),
                            "Status Twojej aplikacji został zmieniony",
                            "Status aplikacji : "+ applicationStatusService.findApplicationStatusDtoById(application.getNrStatus()).getStatus()+".");
                    return "redirect:/admin/applications";
                }
            }
        } else {
            model.addAttribute("failedMessage", "Wniosek posiada już wybrany przez Ciebie status ");
            return "redirect:/admin/application/" + id;
        }

    }

    /**
     * @return page with the department adding form.
     * */
    @GetMapping(value = "/admin/addDepartment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String departmentForm( Model model,
                                 @RequestParam(name = "success", required = false) String success) {
        model.addAttribute("departmentDto", new DepartmentDto());
        if (success != null) {
            if (success.equals("addDepartment")) {
                model.addAttribute("succesMessage", "Dział dodany !");
            }
        }
        return "addDepartment";
    }

    /**
     * Method is responsible for adding the department.
     * @return success = the department is added,
     *         error = page with the department adding form.
     * */
    @PostMapping(value = "/admin/addDepartmentAction")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addDepartment(@Valid DepartmentDto departmentDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentDto", departmentDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "addDepartment";
        }
        if (departmentService.findDepartmentByName(departmentDto.getName()) != null) {
            model.addAttribute("departmentDto", departmentDto);
            model.addAttribute("failedMessage", "taki dział juz istnieje !!!");
            return "addDepartment";
        }
        departmentService.saveDepartment(departmentDto);
        return "redirect:/admin/addDepartment?success=addDepartment";
    }

    /**
     * @return page with the position adding form.
     * */
    @GetMapping(value = "/admin/addPosition")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String positionForm(Model model,
                               @RequestParam(name = "success", required = false) String success) {
        model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
        model.addAttribute("positionDto", new PositionDto());
        if (success != null) {
            if (success.equals("addPosition")) {
                model.addAttribute("succesMessage", "Stanowisko Dodane !");
            }
        }
        return "addPosition";
    }

    /**
     * Method is responsible for adding the position.
     * @return success = the position is added,
     *         error = page with the position adding form.
     * */
    @PostMapping(value = "/admin/addPositionAction")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPosition(@Valid PositionDto positionDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("positionDto", positionDto);
            model.addAttribute("departmentList", departmentService.findAllDepartments());
            model.addAttribute("failedMessage", "coś poszło nie tak!!!");
            return "addPosition";
        }
        if (positionService.findPositionByName(positionDto.getName()) != null) {
            model.addAttribute("positionDto", positionDto);
            model.addAttribute("departmentList", departmentService.findAllDepartments());
            model.addAttribute("failedMessage", "takie stanowisko juz istnieje !!!");
            return "addPosition";
        }
        positionService.savePositionDto(positionDto);
        model.addAttribute("positionDto", new PositionDto());
        return "redirect:/admin/addPosition?success=addPosition";
    }

    /**
     * Method for removing the application.
     * @param id Application ID.
     * @return application removed / page with list of applications.
     * */
    @DELETE
    @RequestMapping(value = "/admin/delete/application/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteApplication(@PathVariable("id") UUID id)  {
        applicationService.deleteApplicationById(id);
        return "redirect:/admin/applications";
    }

    /**
     * Method for removing the department.
     * @param id Department ID.
     * @return department removed / page with company structure.
     * */
    @DELETE
    @RequestMapping(value = "/admin/delete/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteDepartment(@PathVariable("id") int id) {
        positionService.deletePositionByDepartmentId(id);
        departmentService.deleteDepartmentById(id);
        return "redirect:/admin/companyStructure";
    }

    /**
     * Method for removing the position.
     * @param id Department ID.
     * @return department removed / page with company structure.
     * */
    @DELETE
    @RequestMapping(value = "/admin/delete/position/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePosition(@PathVariable("id") int id) {
        positionService.deletePositionById(id);
        return "redirect:/admin/companyStructure";
    }

    /**
     * @return page with Company structure, list of departments and positions.
     * */
    @GetMapping(value = "/admin/companyStructure")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCompanyStructure(Model model) {
        model.addAttribute("departmentDtoList", departmentService.findAllDepartamentDto());
        return "companyStructure";
    }

    /**
     * Method displays a form with department data with the possibility of editing.
     * @param id Employee ID.
     * @return page with the department editing form.
     * */
    @GetMapping(value = "/admin/edit/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editDepartmentForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("departmentDto", departmentService.findDepartmentDtoById(id));
        return "editDepartment";
    }

    /**
     * Method for editing the department.
     * @param id Department ID.
     * @return  success = department is edited / view company structure,
     *          error = page with the department editing form.
     * */
    @PostMapping(value = "/admin/update/department/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editDepartmentAction(@Valid @PathVariable("id") int id, DepartmentDto departmentDto, BindingResult
            result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentDto", departmentDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "editDepartment";
        }
        if (departmentService.findDepartmentByName(departmentDto.getName()) != null) {
            model.addAttribute("blad", "taki dział juz istnieje !!!");
            model.addAttribute("departmentDto", departmentDto);
            return "editDepartment";
        }
        departmentService.updateDepartmentName(departmentDto.getName(), id);
        return "redirect:/admin/companyStructure";
    }

    /**
     * Method displays a form with position data with the possibility of editing.
     * @param id Position ID.
     * @return page with the position editing form.
     * */
    @GetMapping(value = "/admin/edit/position/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPositionForm(@PathVariable("id") int id, PositionDto positionDto, BindingResult result, Model
            model) {
        positionDto = positionService.findPositionDtoById(id);
         int nrDepartment = positionDto.getDepartmentDto().getId();

        positionDto.setNrDepartment(nrDepartment);
        model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
        model.addAttribute("positionDto", positionDto);

        return "editPosition";
    }

    /**
     * Method for editing the position.
     * @param id Position ID.
     * @return  success = position is edited / view company structure,
     *          error = page with the position editing form.
     * */
    @PostMapping(value = "/admin/update/position/{id}")
    public String editPositionAction(@Valid @PathVariable("id") int id, PositionDto positionDto, BindingResult result, Model
            model) {
        if (result.hasErrors()) {
            model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
            model.addAttribute("positionDto", positionDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "editPosition";
        }
        if (positionService.findPositionByName(positionDto.getName()) != null) {
            model.addAttribute("departmentList", departmentService.findAllDepartamentDto());
            model.addAttribute("failedMessage", "takie stanowisko juz istnieje !!!");
            model.addAttribute("positionDto", positionDto);
            return "editPosition";
        }
        positionService.updatePosition(positionDto.getName(), positionDto.getNrDepartment(), id);
        return "redirect:/admin/companyStructure";
    }

    private Employee loggedEmployee() {
        String username = EmployeeModel.getLoggedEmployee();
        Employee employee = employeeService.findEmployeeByEmail(username);
        return employee;
    }

}
