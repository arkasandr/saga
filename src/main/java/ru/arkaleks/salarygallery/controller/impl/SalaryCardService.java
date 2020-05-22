package ru.arkaleks.salarygallery.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Transactional
@Service
public class SalaryCardService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    /**
     * Метод сохраняет нового пользователя User без роли UserRole
     *
     * @param
     * @return
     * @throws
     */



}
