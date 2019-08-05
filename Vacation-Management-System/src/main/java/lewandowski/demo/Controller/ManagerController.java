package lewandowski.demo.Controller;

import lewandowski.demo.DTO.ApplicationDto;
import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Application;
import lewandowski.demo.Model.Employee;
import lewandowski.demo.Service.*;
import lewandowski.demo.Utilities.AppComponentSelectMap;
import lewandowski.demo.Utilities.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ManagerController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationBalanceService vacationBalanceService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AppComponentSelectMap appComponentSelectMap;

    /**
     * Method gets data with a list of applications.
     * @return page with list of applications,
     *         applications's department ID = manager's department ID.
     * */
    @GetMapping(value = "/manager/applications")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getApplicationsByEmployeeDepartmentId(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("applicationList",
        applicationService.findApplicationsDtoEmployeeByDepartment(
                employeeDto.getDepartmentDto().getId(), employeeDto.getId()));
        return "applications";
    }

    /**
     * Method gets data with a list of applications for planned vacation.
     * @return page with list of applications for planned vacation,
     *         applications's department ID = manager's department ID.
     * */
    @GetMapping(value = "/manager/vacationPlans")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getVacationPlansByEmployeeDepartmentId(Model model) {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);
        model.addAttribute("applicationList",
                applicationService.findVacationPlansDtoEmployeeByDepartment(
                        employeeDto.getDepartmentDto().getId(), employeeDto.getId()));
        return "applications";
    }

    /**
     * Method gets application data.
     * @param id Application ID.
     * @return page with application data.
     * */
    @GetMapping(value = "/manager/application/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String getApplication(@PathVariable("id") UUID id, Model model) {
        ApplicationDto applicationDto = applicationService.findApplicationDtoById(id);
        int status = applicationDto.getApplicationStatusDto().getId();
        applicationDto.setNrStatus(status);
        model.addAttribute("applicationStatusMapManager", appComponentSelectMap.prepareStatusMapManager());
        model.addAttribute("applicationDto", applicationDto);
        return "application";
    }

    @PostMapping(value = "/manager/changeStatus/application/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String updateStatusApplication(@PathVariable("id") UUID id, ApplicationDto applicationDto, Model model, BindingResult result,
                                          @RequestParam(name = "nrStatus") Integer nrStatus) throws ParseException {
        String username = EmployeeModel.getLoggedEmployee();
        EmployeeDto employeeDto = employeeService.findByEmail(username);

        ApplicationDto applicationCurrent = applicationService.findApplicationDtoById(id);
        DateFormat formatDate = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        String stringDate = formatDate.format(new Date());
        Date date = formatDate.parse(stringDate);

        int status = applicationCurrent.getApplicationStatusDto().getId();
        applicationCurrent.setNrStatus(status);

        if (result.hasErrors()) {
            model.addAttribute("applicationStatusMapManager", appComponentSelectMap.prepareStatusMapManager());
            model.addAttribute("applicationDto", applicationDto);
            return "application";
        }
        if (applicationCurrent.getApplicationStatusDto().getId() != applicationDto.getNrStatus()) {
            if (applicationCurrent.getApplicationStatusDto().getId() != 1) {
                if (applicationDto.getNrStatus() == 4 || applicationDto.getNrStatus() == 5) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation;
                        updateLeaveVacation = vacationLeaveBalance + vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        model.addAttribute("application", new Application());
                        return "redirect:/manager/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/manager/applications";
                    }
                } else if (applicationDto.getNrStatus() == 2) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation = vacationLeaveBalance - vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance - vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        model.addAttribute("application", new Application());
                        return "redirect:/manager/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/manager/applications";
                    }
                } else {
                    applicationService.updateStatusApplication(nrStatus, id);
                    return "redirect:/manager/applications";
                }
            } else {
                if (applicationDto.getNrStatus() == 4 || applicationDto.getNrStatus() == 5) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        int vacationDays = applicationCurrent.getVacationDays();
                        int vacationLeaveBalance;
                        vacationLeaveBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getVacationLeave();
                        int vacationAnnualBalance = vacationBalanceService.findByEmployee_IdAndYear(applicationCurrent.getEmployeeDto().getId(), date).getAnnualVacation();
                        int updateLeaveVacation;
                        updateLeaveVacation = vacationLeaveBalance + vacationDays;
                        int updateAnnualBalance = vacationAnnualBalance + vacationDays;

                        vacationBalanceService.updateVacationLeave(updateLeaveVacation, applicationCurrent.getEmployeeDto().getId(), date);
                        vacationBalanceService.updateAnnualLeave(updateAnnualBalance, applicationCurrent.getEmployeeDto().getId(), date);

                        applicationService.updateStatusApplication(nrStatus, id);
                        model.addAttribute("application", new Application());
                        return "redirect:/manager/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/manager/applications";
                    }
                } else if (applicationDto.getNrStatus() == 2) {
                    if (applicationCurrent.getVacationTypeDto().getId() == 1) {
                        applicationService.updateStatusApplication(nrStatus, id);
                        model.addAttribute("application", new Application());
                        return "redirect:/manager/applications";
                    } else {
                        applicationService.updateStatusApplication(nrStatus, id);
                        return "redirect:/manager/applications";
                    }
                } else {
                    applicationService.updateStatusApplication(nrStatus, id);
                    return "redirect:/manager/applications";
                }

            }
        } else {
            model.addAttribute("blad", "Wniosek posiada już wybrany przez Ciebie status ");
            return "redirect:/manager/application/" + id;
        }
    }

    private Employee loggedEmployee() {
        String username = EmployeeModel.getLoggedEmployee();
        Employee employee = employeeService.findEmployeeByEmail(username);
        return employee;
    }
}
