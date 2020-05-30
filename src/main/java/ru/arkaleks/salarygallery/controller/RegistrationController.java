package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.impl.RegistrationService;
import ru.arkaleks.salarygallery.model.Employee;


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


    /**
     * Метод сохраняет нового пользователя User
     *
     * @param
     * @return UserDTO
     * @throws
     */
    @ResponseBody
    @PostMapping("/registrationstart/continue")
    EmployeeDto registerNewEmployee(@RequestBody Employee newEmployee) {
        registrationService.saveEmployeeWithoutEmployeeRole(newEmployee);
//        registrationService.setUserRoleToEmployee(newEmployee);
        return registrationService.addNewEmployee(newEmployee);
    }


}
