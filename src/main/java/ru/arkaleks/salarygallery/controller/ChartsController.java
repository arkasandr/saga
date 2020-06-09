package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.ChartsService;
import ru.arkaleks.salarygallery.controller.impl.CurrentUserService;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;
import ru.arkaleks.salarygallery.repository.PaySlipRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class ChartsController {

    @Autowired
    ChartsService chartsService;

    @Autowired
    PaySlipRepository paySlipRepository;

    @Autowired
    CurrentUserService currentUserService;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/all")
    List<PaySlipDto> getChartForAllPaySlips() throws ParseException, IOException {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findBy(employee);
        chartsService.createChartAsPNGFile(paySlips);
        return employeeMapper.mapToPaySlipDtoList(paySlips);
    }

}
