package lewandowski.demo.Utilities;

import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.DTO.VacationTypeDto;
import lewandowski.demo.Model.VacationType;
import lewandowski.demo.Service.DepartmentService;
import lewandowski.demo.Service.VacationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class VacationTypeEditor extends PropertyEditorSupport {

    @Autowired
    private VacationTypeService vacationTypeService;

    @Override
    public void setAsText(String text) {
        VacationTypeDto v = this.vacationTypeService.findVacationTypeDtoById(Integer.valueOf(text));

        this.setValue(v);
    }

}