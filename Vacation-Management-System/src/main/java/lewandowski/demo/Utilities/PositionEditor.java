package lewandowski.demo.Utilities;

import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.DTO.PositionDto;
import lewandowski.demo.Service.DepartmentService;
import lewandowski.demo.Service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class PositionEditor extends PropertyEditorSupport {

    @Autowired
    private PositionService positionService;

    @Override
    public void setAsText(String text) {
        PositionDto p = this.positionService.findPositionDtoById(Integer.valueOf(text));

        this.setValue(p);
    }

}