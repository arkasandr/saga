package ru.arkaleks.salarygallery.controller.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.arkaleks.salarygallery.controller.dto.DocumentPdfDto;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.model.DocumentPdf;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    List<EmployeeDto> mapToEmployeeDtoList(List<Employee> employeeList);

    EmployeeDto mapToEmployeeDto(Employee employeeEntity);

    List<PaySlipDto> mapToSalaryCardDtoList(List<PaySlip> paySlipList);

    PaySlipDto mapToSalaryCardDto(PaySlip paySlipEntity);

    DocumentPdfDto mapToDocumentPdfDto(DocumentPdf documentPdfEntity);

}
