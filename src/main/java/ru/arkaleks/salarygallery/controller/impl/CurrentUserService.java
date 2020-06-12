package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.service.UserDetailsAdapter;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class CurrentUserService {

    /**
     * Метод возвращает сотрудника Employee  из текущего контекста
     *
     * @param
     * @return UserDetailsAdapter
     * @throws
     */
    public UserDetailsAdapter getCurrentEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter currentEmployee = (UserDetailsAdapter) auth.getPrincipal();
        return currentEmployee;
    }

    /**
     * Метод возвращает сотрудника Employee из текущего контекста
     *
     * @param
     * @return Employee
     * @throws
     */
    public Employee getLogInEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter currentEmployee = (UserDetailsAdapter) auth.getPrincipal();
        return currentEmployee.getEmployee();
    }


    /**
     * Метод устанавливает сотрудника Employee в текущий контекст
     *
     * @param
     * @return Employee
     * @throws
     */
    public void setLogInEmployee(Employee employee) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter currentEmployee = (UserDetailsAdapter) auth.getPrincipal();
        currentEmployee.setEmployee(employee);
    }
}
