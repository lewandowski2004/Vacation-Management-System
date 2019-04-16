package lewandowski.demo.Controller;

import lewandowski.demo.DAO.ApplicationRepository;
import lewandowski.demo.DTO.*;
import lewandowski.demo.Model.*;
import lewandowski.demo.Utilities.*;
import lewandowski.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.ws.rs.DELETE;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AppComponentSelectMap appComponentSelectMap;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private VacationTypeService vacationTypeService;

    @Autowired
    private VacationBalanceService vacationBalanceService;

    @Autowired
    private VacationTypeEditor vacationTypeEditor;

    @Autowired
    private EmailSender emailSender;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(VacationTypeDto.class, this.vacationTypeEditor);
    }

    /**
     * @return Main page with information about annual vacation, emergency vacation, leave vacation.
     * */
    @GetMapping(value = "/")
    public String index(EmployeeDto employeeDto, VacationBalanceDto vacationBalanceDto, BindingResult result, Model model) throws ParseException {
        String username = EmployeeModel.getLoggedEmployee();
        employeeDto = employeeService.findByEmail(username);
        Date dateAddition = appComponentSelectMap.returnDateByFormat("yyyy", new Date());

        List<EmployeeDto> listEmployeeWithUpdateVacation = employeeService.findAllEmployeesDtoByVacationBalancesWhereYearIs(appComponentSelectMap.DateFormat(new Date()));
        List<UUID> listIdEmployeeWIthUpdateVacation = new ArrayList<>();
        for (EmployeeDto employeeDtoWithUpdateVacation : listEmployeeWithUpdateVacation){
            UUID id = employeeDtoWithUpdateVacation.getId();
            listIdEmployeeWIthUpdateVacation.add(id);
        }

        if (vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition) == null) {
            model.addAttribute("employeeDto", employeeDto);
            return "index";
        }
        if(employeeService.findEmployeesByIdNotIn(listIdEmployeeWIthUpdateVacation).size() == 0){
            model.addAttribute("color","color: green");
            model.addAttribute("glyphicon","glyphicon glyphicon-ok");
            model.addAttribute("content","Wszyscy pracownicy posiadają zaktualizowany urlop.");
        }else {
            model.addAttribute("color","color: red");
            model.addAttribute("glyphicon","glyphicon glyphicon-exclamation-sign");
            model.addAttribute("content","W systemie są pracownicy bez Aktualnego urlopu.");
        }
        model.addAttribute("annualVacation", vacationBalanceService.findVacationBalanceDtoByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getAnnualVacation());
        model.addAttribute("emergencyVacation", vacationBalanceService.findVacationBalanceDtoByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getEmergencyVacation());
        model.addAttribute("vacationLeave", vacationBalanceService.findVacationBalanceDtoByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLeave());
        model.addAttribute("vacationLimit", vacationBalanceService.findVacationBalanceDtoByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLimit());
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("vacationBalanceDto", vacationBalanceDto);
        return "index";
    }

    /**
     * @return page with information about vacation history.
     * */
    @GetMapping(value = "/vacationInformation")
    public String vacationInformation(EmployeeDto employeeDto, VacationBalanceDto vacationBalanceDto, Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        employeeDto = employeeService.findByEmail(username);
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("vacationBalanceDto", vacationBalanceDto);
        return "info";
    }

    /**
     * @return page with the form of adding planned vacation.
     * */
    @GetMapping(value = "/addVacationPlan")
    public String addVacationPlanForm(ApplicationDto applicationDto, Model model,
                                  @RequestParam(name = "success", required = false) String success) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("applicationDto", applicationDto);
        model.addAttribute("employeeDto", employeeDto);
        if (success != null) {
            if (success.equals("addVacationPlan")) {
                model.addAttribute("succesMessage", "Wniosek złożony !");
            }
        }
        return "addVacationPlan";
    }

    /**
     * Adding an application by a logged employee.
     * @return success = adding the application / update the vacation balance,
     *         error = page with the form of adding planned vacation.
     */
    @PostMapping(value = "/addVacationPlanAction")
    public String addVacationPlanAction(ApplicationDto applicationDto, BindingResult result, Model model) throws ParseException {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto();
        VacationTypeDto vacationTypeDto = new VacationTypeDto();

        Date dateAddition = appComponentSelectMap.returnDateByFormat("yyyy", new Date());

        Date startDate = appComponentSelectMap.returnDateByFormat("yyyy-MM-dd", applicationDto.getStartOfVacation());

        Date endDate = appComponentSelectMap.returnDateByFormat("yyyy-MM-dd", applicationDto.getEndOfVacation());

        List<String> stringDatesBetweenStartAndEndDatesVacation = appComponentSelectMap.listStringDatesBetweenStartAndEndDatesVacation(startDate,endDate);


        if (result.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            return "addVacationPlan";
        }
        for(String item : stringDatesBetweenStartAndEndDatesVacation){
            if (applicationRepository.findAllApplicationsWithDuplicateDate(employeeDto.getId(),item).isEmpty() != true) {
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("failedMessage", "Urlop w danym terminie jest już zaplanowany !");
                return "addVacationPlan";
            }
        }
        if (vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition) == null) {
            model.addAttribute("failedMessage", "Twój roczny urlop nie został zdefiniowany ");
            model.addAttribute("employeeDto", employeeDto);
            return "addVacationPlan";
        }
        if (applicationDto.getVacationDays() > vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLeave() == true) {
                model.addAttribute("failedMessage", "Nie masz wystarczającej liczby dni urlopu");
                model.addAttribute("employeeDto", employeeDto);
                return "addVacationPlan";
        } else {
            int vacationDays = applicationDto.getVacationDays();
            int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLeave();
            int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getAnnualVacation();
            int updateLeaveVacation = vacationLeaveBalance - vacationDays;
            int updateAnnualBalance = vacationAnnualBalance - vacationDays;

            vacationBalanceService.updateVacationLeave(updateLeaveVacation, employeeDto.getId(), dateAddition);
            vacationBalanceService.updateAnnualLeave(updateAnnualBalance, employeeDto.getId(), dateAddition);
            applicationDto.setEmployeeDto(employeeDto);
            applicationStatusDto.setId(1);
            vacationTypeDto.setId(1);
            applicationDto.setVacationTypeDto(vacationTypeDto);
            applicationDto.setApplicationStatusDto(applicationStatusDto);
            applicationService.saveApplicationDto(applicationDto);

            model.addAttribute("applicationDto", new ApplicationDto());
            model.addAttribute("employeeDto", employeeDto);
            return "redirect:/addVacationPlan?success=addVacationPlan";
        }

    }

    /**
     * @return page with the form of adding application.
     * */
    @GetMapping(value = "/addApplication")
    public String applicationForm(ApplicationDto applicationDto, Model model,
                                  @RequestParam(name = "success", required = false) String success) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
        model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
        model.addAttribute("applicationDto", applicationDto);
        model.addAttribute("employeeDto", employeeDto);
        if (success != null) {
            if (success.equals("addApplication")) {
                model.addAttribute("succesMessage", "Wniosek złożony !");
            }
        }
        return "addApplication";
    }

    /**
     * Adding an application by a logged employee.
     * @return success = adding the application / update the vacation balance,
     *         error = page with the form of adding application.
     */
    @PostMapping(value = "/addApplicationAction")
    public String addApplicationAction(ApplicationDto applicationDto, BindingResult result, Model model) throws ParseException {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);

        return ApplicationAction(applicationDto,employeeDto,result,model,
                           "addApplication","redirect:/addApplication?success=addApplication");
    }

    /**
     * Method gets data with a list of applications.
     * @return page with list of logged employee applications.
     *         applications in which employee ID = logged employee ID.
     * */
    @GetMapping(value = "/applications")
    public String getApplicationsEmployee(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        UUID employeeId = employeeDto.getId();
        model.addAttribute("applicationList", applicationService.findApplicationsByEmployee_IdAndVacationPlanOrderByDateOfAdditionDescDto(employeeId, false));
        model.addAttribute("employeeDto", employeeDto);
        return "applications";
    }

    /**
     * Method gets data with a list of applications.
     * @return page with list of logged employee applications.
     *         applications in which employee ID = logged employee ID.
     * */
    @GetMapping(value = "/vacationPlans")
    public String getVacationPlansEmployee(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        UUID employeeId = employeeDto.getId();
        model.addAttribute("applicationList", applicationService.findVacationPlansDtoEmployeeByDepartment(employeeId, true));
        model.addAttribute("employeeDto", employeeDto);
        return "applications";
    }

    /**
     * Method gets application data.
     * @param id Application ID.
     * @return page with application data.
     * */
    @GetMapping(value = "/application/{id}")
    public String getApplication(@PathVariable("id") UUID id, Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        model.addAttribute("applicationDto", applicationService.findApplicationDtoById(id));
        model.addAttribute("employeeDto", employeeService.findByEmail(username));
        return "application";
    }

    /**
     * Method for removing the application.
     * @param id Application ID.
     * @return success = application removed / update employee's vacation balance / page with list of applications,
     *         application accepted = statement / page with application data.
     * */
    @DELETE
    @RequestMapping(value = "/delete/application/{id}")
    public String deleteApplication(@PathVariable("id") UUID id, Application application, BindingResult result, Model model) throws ParseException {
        application = applicationService.findApplicationById(id);

        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);

        DateFormat formatDate = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String stringDate = formatDate.format(new Date());
        Date date = formatDate.parse(stringDate);

        if (application.getApplicationStatus().getId() == 2 || application.getApplicationStatus().getId() == 3){
            model.addAttribute("infoMassage", "Wniosek został zaakceptowany. W razie jakichkolwiek zmian prosze o kontkat z przełożonym.");
            return "redirect:/application/"+id;
        }
        if (application.getApplicationStatus().getId() != 4) {
            if (application.getVacationType().getId() == 1) {
                if (result.hasErrors()) {
                    return "redirect:/applications";
                } else {
                    int vacationDays = application.getVacationDays();
                    int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), date).getVacationLeave();
                    int vacationAnnualBalance;
                    vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), date).getAnnualVacation();
                    int updateLeaveVacation = vacationLeaveBalance + vacationDays;
                    int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                    vacationBalanceService.updateVacationLeave(updateLeaveVacation, employeeDto.getId(), date);
                    vacationBalanceService.updateAnnualLeave(updateAnnualBalance, employeeDto.getId(), date);

                    applicationService.deleteApplication(application);
                    model.addAttribute("application", new Application());
                    return "redirect:/applications";
                }

            } else if (application.getVacationType().getId() == 2) {

                if (result.hasErrors()) {
                    return "redirect:/applications";
                } else {
                    int vacationDays = application.getVacationDays();
                    int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), date).getEmergencyVacation();
                    int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), date).getAnnualVacation();
                    int updateEmergencyVacation = vacationLeaveBalance + vacationDays;
                    int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                    vacationBalanceService.updateEmergencyLeave(updateEmergencyVacation, employeeDto.getId(), date);
                    vacationBalanceService.updateAnnualLeave(updateAnnualBalance, employeeDto.getId(), date);

                    applicationService.deleteApplication(application);
                    model.addAttribute("application", new Application());
                    return "redirect:/applications";
                }
            } else {
                applicationService.deleteApplication(application);
                return "redirect:/applications";
            }
        } else {
            applicationService.deleteApplication(application);
            return "redirect:/applications";
        }
    }

    private Employee loggedEmployee() {
        String username = EmployeeModel.getLoggedEmployee();
        Employee employee = employeeService.findEmployeeByEmail(username);
        return employee;
    }


    public String ApplicationAction(ApplicationDto applicationDto, EmployeeDto employeeDto, BindingResult result, Model model , String view, String redirect) throws ParseException {
        ApplicationStatusDto applicationStatusDto = new ApplicationStatusDto();

        Date dateAddition = appComponentSelectMap.returnDateByFormat("yyyy", new Date());

        Date startDate = appComponentSelectMap.returnDateByFormat("yyyy-MM-dd", applicationDto.getStartOfVacation());

        Date endDate = appComponentSelectMap.returnDateByFormat("yyyy-MM-dd", applicationDto.getEndOfVacation());

        List<String> stringDatesBetweenStartAndEndDatesVacation = appComponentSelectMap.listStringDatesBetweenStartAndEndDatesVacation(startDate,endDate);


        if (result.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("failedMessage", "Coś poszło nie tak !");
            model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
            model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
            return view;
        }
        for(String item : stringDatesBetweenStartAndEndDatesVacation){
            if (applicationRepository.findAllApplicationsWithDuplicateDate(employeeDto.getId(),item).isEmpty() != true) {
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("failedMessage", "Urlop w wyznaczonym terminie juz istnieje !");
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
                return view;
            }
        }
        if (vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition) == null) {
            model.addAttribute("failedMessage", "Twój roczny urlop nie został zdefiniowany ");
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
            model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
            return view;
        } else if (applicationDto.getVacationTypeDto().getId() == 1) {
            if (startDate.compareTo(appComponentSelectMap.returnDateByFormat("yyyy-MM-dd", new Date())) == 0) {
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("failedMessage", "Wystawienie wnioska o urlop wypoczynkowy wymaga jednego dnia uprzedzenia !");
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
                return view;
            }
            if (applicationDto.getVacationDays() > vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLeave() == true) {
                model.addAttribute("failedMessage", "Nie masz wystarczającej liczby dni urlopu");
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
                return view;
            } else {
                int vacationDays = applicationDto.getVacationDays();
                int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getVacationLeave();
                int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getAnnualVacation();
                int updateLeaveVacation = vacationLeaveBalance - vacationDays;
                int updateAnnualBalance = vacationAnnualBalance - vacationDays;

                vacationBalanceService.updateVacationLeave(updateLeaveVacation, employeeDto.getId(), dateAddition);
                vacationBalanceService.updateAnnualLeave(updateAnnualBalance, employeeDto.getId(), dateAddition);
                applicationDto.setEmployeeDto(employeeDto);
                applicationStatusDto.setId(1);
                applicationDto.setApplicationStatusDto(applicationStatusDto);
                applicationService.saveApplicationDto(applicationDto);

                model.addAttribute("applicationDto", new ApplicationDto());
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
                return redirect;
            }
        } else if (applicationDto.getVacationTypeDto().getId() == 2) {
            if (applicationDto.getVacationDays() > vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getEmergencyVacation() == true) {
                model.addAttribute("blad", "Nie masz wystarczającej liczby dni urlopu na żądanie");
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
            } else {
                int vacationDays = applicationDto.getVacationDays();
                int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getEmergencyVacation();
                int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(employeeDto.getId(), dateAddition).getAnnualVacation();
                int updateEmergencyVacation = vacationLeaveBalance - vacationDays;
                int updateAnnualBalance = vacationAnnualBalance - vacationDays;

                vacationBalanceService.updateEmergencyLeave(updateEmergencyVacation, employeeDto.getId(), dateAddition);
                vacationBalanceService.updateAnnualLeave(updateAnnualBalance, employeeDto.getId(), dateAddition);
                applicationDto.setEmployeeDto(employeeDto);
                applicationStatusDto.setId(3);
                applicationDto.setApplicationStatusDto(applicationStatusDto);
                applicationService.saveApplicationDto(applicationDto);

                model.addAttribute("applicationDto", new ApplicationDto());
                model.addAttribute("employeeDto", employeeDto);
                model.addAttribute("listOfEmployeesForReplacement", employeeService.findEmployeesByPositionId(employeeDto.getPositionDto().getId()));
                model.addAttribute("vacationTypeList", vacationTypeService.findAllVacationsTypeDto());
                return redirect;
            }
        } else {
            applicationDto.setEmployeeDto(employeeDto);
            applicationStatusDto.setId(1);
            applicationDto.setApplicationStatusDto(applicationStatusDto);
            applicationService.saveApplicationDto(applicationDto);
            return redirect;
        }
        return view;
    }
    @GetMapping(value = "/sendEmailg")
    public String sendEmailg(Model model) throws MessagingException {

        return "email";

    }
    // Test Method
    @GetMapping(value = "/sendEmail")
    @ResponseBody
    public String sendEmail(Model model) {

        emailSender.sendMessage("lewandowski2004@o2.pl","adas","asdad");
       //s emailSender.sendMessage("lewandowski2004@o2.pl", "ja", "asdas");
         return "email";

    }

}
