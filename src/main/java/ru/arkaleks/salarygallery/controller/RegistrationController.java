package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.CurrentUserService;
import ru.arkaleks.salarygallery.controller.impl.RegistrationService;
import ru.arkaleks.salarygallery.model.Employee;

import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CurrentUserService currentUserService;


    /**
     * Метод регистрирует нового сотрудника Employee
     *
     * @param
     * @return EmployeeDto
     * @throws
     */
    @ResponseBody
    @PostMapping("/registrationstart/continue")
    EmployeeDto registerNewEmployee(@RequestBody Employee newEmployee) {
        registrationService.saveEmployeeWithoutEmployeeRole(newEmployee);
        registrationService.setUserRoleToEmployee(newEmployee);
        return registrationService.addNewEmployee(newEmployee);
    }

    /**
     * Метод обновляет информацию о зарегестрированном сотруднике Employee
     *
     * @param
     * @return EmployeeDto
     * @throws
     */
    @ResponseBody
    @PostMapping("/registrationend/adddata")
    EmployeeDto addNewEmployeeData(@RequestBody Employee newEmployee) {
        return registrationService.updateEmployeeByUsername(newEmployee);
    }


    /**
     * Метод вовращает имя зарегистрированного сотрудника Employee
     *
     * @param
     * @return EmployeeDto
     * @throws
     */
    @ResponseBody
    @GetMapping("registrationstart/current")
    String getCurrentEmployeeUsername() {
        return currentUserService.getCurrentEmployeeUsername();
    }
}
