package lewandowski.demo.Service;

import lewandowski.demo.DTO.EmployeeDto;
import lewandowski.demo.Model.Application;
import lewandowski.demo.Model.Employee;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@Transactional
public class GenerateExcelService {

    public boolean generateExcelEmployees(List<Employee> employees, ServletContext context, HttpServletRequest request,
                                 HttpServletResponse response){
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists){
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file+"/"+"employees"+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("EMployees");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow hssfRow = workSheet.createRow(0);

            HSSFCell firstName = hssfRow.createCell(0);
            firstName.setCellValue("First Name");
            firstName.setCellStyle(hssfCellStyle);

            HSSFCell lastName = hssfRow.createCell(1);
            lastName.setCellValue("Last Name");
            lastName.setCellStyle(hssfCellStyle);

            HSSFCell email = hssfRow.createCell(2);
            email.setCellValue("Email");
            email.setCellStyle(hssfCellStyle);

            HSSFCell pesel = hssfRow.createCell(3);
            pesel.setCellValue("PESEL");
            pesel.setCellStyle(hssfCellStyle);

            HSSFCell dateOfBirth = hssfRow.createCell(4);
            dateOfBirth.setCellValue("Data of birth");
            dateOfBirth.setCellStyle(hssfCellStyle);

            HSSFCell addressLine1 = hssfRow.createCell(5);
            addressLine1.setCellValue("Address line 1");
            addressLine1.setCellStyle(hssfCellStyle);

            HSSFCell addressLine2 = hssfRow.createCell(6);
            addressLine2.setCellValue("Address line 2");
            addressLine2.setCellStyle(hssfCellStyle);

            HSSFCell city = hssfRow.createCell(7);
            city.setCellValue("City");
            city.setCellStyle(hssfCellStyle);

            HSSFCell zipCode = hssfRow.createCell(8);
            zipCode.setCellValue("Zip Code");
            zipCode.setCellStyle(hssfCellStyle);

            HSSFCell phoneNumber = hssfRow.createCell(9);
            phoneNumber.setCellValue("Phone Number");
            phoneNumber.setCellStyle(hssfCellStyle);

            HSSFCell department = hssfRow.createCell(10);
            department.setCellValue("Department");
            department.setCellStyle(hssfCellStyle);

            HSSFCell position = hssfRow.createCell(11);
            position.setCellValue("Position");
            position.setCellStyle(hssfCellStyle);



            int i = 1;
            for(Employee employee : employees){

                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell firstNameValue = bodyRow.createCell(0);
                firstNameValue.setCellValue(employee.getName());
                firstNameValue.setCellStyle(bodyCellStyle);

                HSSFCell lastNameValue = bodyRow.createCell(1);
                lastNameValue.setCellValue(employee.getLastName());
                lastNameValue.setCellStyle(bodyCellStyle);

                HSSFCell emailValue = bodyRow.createCell(2);
                emailValue.setCellValue(employee.getEmail());
                emailValue.setCellStyle(bodyCellStyle);

                HSSFCell peselValue = bodyRow.createCell(3);
                peselValue.setCellValue(employee.getPesel());
                peselValue.setCellStyle(bodyCellStyle);

                HSSFCell dateOfBirthValue = bodyRow.createCell(4);
                dateOfBirthValue.setCellValue(employee.getDateOfBirth());
                dateOfBirthValue.setCellStyle(bodyCellStyle);

                HSSFCell addressLine1Value = bodyRow.createCell(5);
                addressLine1Value.setCellValue(employee.getAddressLine1());
                addressLine1Value.setCellStyle(bodyCellStyle);

                HSSFCell addressLine2Value = bodyRow.createCell(6);
                addressLine2Value.setCellValue(employee.getAddressLine2());
                addressLine2Value.setCellStyle(bodyCellStyle);

                HSSFCell cityValue = bodyRow.createCell(7);
                cityValue.setCellValue(employee.getCity());
                cityValue.setCellStyle(bodyCellStyle);

                HSSFCell zipCodeValue = bodyRow.createCell(8);
                zipCodeValue.setCellValue(employee.getZipCode());
                zipCodeValue.setCellStyle(bodyCellStyle);

                HSSFCell phoneNumberValue = bodyRow.createCell(9);
                phoneNumberValue.setCellValue(employee.getPhoneNumber());
                phoneNumberValue.setCellStyle(bodyCellStyle);

                HSSFCell departmentValue = bodyRow.createCell(10);
                departmentValue.setCellValue(employee.getDepartment().getName());
                departmentValue.setCellStyle(bodyCellStyle);

                HSSFCell positionValue = bodyRow.createCell(11);
                positionValue.setCellValue(employee.getPosition().getName());
                positionValue.setCellStyle(bodyCellStyle);

                i++;
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e){
            return false;

        }
    }

    public boolean generateExcelEmployeesVacationBalance(List<EmployeeDto> employeeDtos, ServletContext context, HttpServletRequest request,
                                                         HttpServletResponse response){
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists){
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file+"/"+"employeesVacationBalance"+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("EMployeesVacationBalance");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow hssfRow = workSheet.createRow(0);

            HSSFCell firstName = hssfRow.createCell(0);
            firstName.setCellValue("Imie i Nazwisko");
            firstName.setCellStyle(hssfCellStyle);

            HSSFCell department = hssfRow.createCell(1);
            department.setCellValue("Dział");
            department.setCellStyle(hssfCellStyle);

            HSSFCell vacationLimit = hssfRow.createCell(2);
            vacationLimit.setCellValue("Limit roczny [dni]");
            vacationLimit.setCellStyle(hssfCellStyle);

            HSSFCell annualVacation = hssfRow.createCell(3);
            annualVacation.setCellValue("Dostępny urlop [dni]");
            annualVacation.setCellStyle(hssfCellStyle);

            HSSFCell vacationLeave = hssfRow.createCell(4);
            vacationLeave.setCellValue("Urlop wypoczynkowy [dni]");
            vacationLeave.setCellStyle(hssfCellStyle);

            HSSFCell emergencyLeave = hssfRow.createCell(5);
            emergencyLeave.setCellValue("Urlop na żądanie [dni]");
            emergencyLeave.setCellStyle(hssfCellStyle);

            int i = 1;
            for(EmployeeDto employeeDto : employeeDtos){

                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell firstNameLastNameValue = bodyRow.createCell(0);
                firstNameLastNameValue.setCellValue(employeeDto.getName()+" "+employeeDto.getLastName());
                firstNameLastNameValue.setCellStyle(bodyCellStyle);

                HSSFCell departmentValue = bodyRow.createCell(1);
                departmentValue.setCellValue(employeeDto.getDepartmentDto().getName());
                departmentValue.setCellStyle(bodyCellStyle);

                HSSFCell vacationLimitValue = bodyRow.createCell(2);
                vacationLimitValue.setCellValue(employeeDto.getVacationBalancesDto().iterator().next().getVacationLimit());
                vacationLimitValue.setCellStyle(bodyCellStyle);

                HSSFCell annualVacationValue = bodyRow.createCell(3);
                annualVacationValue.setCellValue(employeeDto.getVacationBalancesDto().iterator().next().getAnnualVacation());
                annualVacationValue.setCellStyle(bodyCellStyle);

                HSSFCell vacationLeaveValue = bodyRow.createCell(4);
                vacationLeaveValue.setCellValue(employeeDto.getVacationBalancesDto().iterator().next().getVacationLeave());
                vacationLeaveValue.setCellStyle(bodyCellStyle);

                HSSFCell emergencyVacationValue = bodyRow.createCell(5);
                emergencyVacationValue.setCellValue(employeeDto.getVacationBalancesDto().iterator().next().getEmergencyVacation());
                emergencyVacationValue.setCellStyle(bodyCellStyle);

                i++;
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e){
            return false;

        }
    }
}
