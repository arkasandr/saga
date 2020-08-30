package ru.arkaleks.salarygallery.controller.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Service
@Mapper
public interface EmployeeMapper {

    EmployeeDto mapToEmployeeDto(Employee employeeEntity);

    List<PaySlipDto> mapToPaySlipDtoList(List<PaySlip> paySlipList);

    PaySlipDto mapToPaySlipDto(PaySlip paySlipEntity);

}
