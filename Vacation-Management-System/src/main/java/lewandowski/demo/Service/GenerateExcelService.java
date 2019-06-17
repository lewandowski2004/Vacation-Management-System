package lewandowski.demo.Service;

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

    public boolean generateExcel(List<Employee> employees, ServletContext context, HttpServletRequest request,
                                 HttpServletResponse response){
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if ((!exists)){
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file+"/"+"employees"+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("EMployees");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow hssfRow = workSheet.createRow(0);
            HSSFCell firstName = hssfRow.createCell(0);
            firstName.setCellValue("First Name");
            firstName.setCellStyle(hssfCellStyle);

            int i = 1;
            for(Employee employee : employees){
                HSSFRow bodyRow = workSheet.createRow(i);
                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell firstNameValue = bodyRow.createCell(0);
                firstNameValue.setCellValue(employee.getName());
                firstNameValue.setCellStyle(bodyCellStyle);
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
