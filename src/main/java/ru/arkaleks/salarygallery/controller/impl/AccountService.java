package ru.arkaleks.salarygallery.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Autowired
    private CurrentUserService currentUserService;


    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws IOException
     */
    public List<PaySlipDto> findAllEmployeePayslips() {
        String username = currentUserService.getCurrentEmployee().getUsername();
        return employeeMapper.mapToPaySlipDtoList(employeeRepository
                .findByUsername(username).get().getPaySlips());
    }
}
