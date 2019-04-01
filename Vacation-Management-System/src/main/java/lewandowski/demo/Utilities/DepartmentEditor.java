package lewandowski.demo.Utilities;

import lewandowski.demo.DTO.DepartmentDto;
import lewandowski.demo.Service.DepartmentService;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;
import java.util.Currency;

@Component
public class DepartmentEditor  extends PropertyEditorSupport {

    @Autowired
    private DepartmentService departmentService;

    /*private BaseDaoSupport dao;
    private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

    public DepartmentEditor(BaseDaoSupport dao) {
        this.dao = dao;
    }

    @Override
    public String getAsText() {
        Object obj = getValue();
        if(obj == null) {
            return null;
        }

        if(obj instanceof DepartmentDto) {
            DepartmentDto domainObject = (DepartmentDto) obj;

            return typeConverter.convertIfNecessary(
                    domainObject.getId(), String.class);
        }

        throw new IllegalArgumentException("Value must be a DomainObject");
    }*/


    @Override
    public void setAsText(String text) {
        DepartmentDto d = this.departmentService.findDepartmentDtoById(Integer.valueOf(text));

        this.setValue(d);
    }

}