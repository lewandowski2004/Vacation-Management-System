package lewandowski.demo.Service;

import lewandowski.demo.DAO.VacationTypeRepository;
import lewandowski.demo.DTO.VacationBalanceDto;
import lewandowski.demo.DTO.VacationTypeDto;
import lewandowski.demo.Model.VacationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacationTypeServiceImpl implements VacationTypeService{

    @Autowired
    private VacationTypeRepository vacationTypeRepository;

    /*Entity method*/

    @Override
    public VacationType findVacationTypeById(int id) {
        return vacationTypeRepository.findById(id);
    }

    @Override
    public List<VacationType> findAllVacationsType() {
        return vacationTypeRepository.findAll();
    }

    /*Dto method*/

    @Override
    public VacationTypeDto findVacationTypeDtoById(int id) {
        VacationType vacationType = vacationTypeRepository.findById(id);
        if (vacationType != null) {
            return getVacationTypeDto(vacationType);
        } else {
            return null;
        }
    }

    @Override
    public List<VacationTypeDto> findAllVacationsTypeDto() {
        return findAllvacationsTypeDto(vacationTypeRepository.findAll());
    }

    public List<VacationTypeDto> findAllvacationsTypeDto(List<VacationType> vacationTypeList) {
        List<VacationTypeDto> vacationTypeDtoList = new ArrayList<>();
        for (VacationType vacationType : vacationTypeList) {
            VacationTypeDto vacationTypeDto = getVacationTypeDto(vacationType);
            vacationTypeDtoList.add(vacationTypeDto);
        }
        return vacationTypeDtoList;
    }
    public VacationType getVacationType(VacationTypeDto vacationTypeDto) {
        return VacationType.builder()
                .id(vacationTypeDto.getId())
                .type(vacationTypeDto.getType())
                .build();
    }
    public VacationTypeDto getVacationTypeDto(VacationType vacationType) {
        return VacationTypeDto.builder()
                .id(vacationType.getId())
                .type(vacationType.getType())
                .build();
    }
}
