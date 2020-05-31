package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.EmployeeRole;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Transactional
@Service
public class RegistrationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    /**
     * Метод сохраняет нового сотрудника Employee без роли EmployeeRole
     *
     * @param
     * @return
     * @throws
     */
    public void saveEmployeeWithoutEmployeeRole(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getUsername().equals(newEmployee.getUsername())) {
                throw new IllegalArgumentException("Sorry, User with Username = " + employee.getUsername() + " is exists!");
            }
        }
        Employee addEmployee = new Employee(newEmployee.getUsername(), newEmployee.getPassword(), newEmployee.getEmail());
        addEmployee.setPassword(bCryptPasswordEncoder.encode(newEmployee.getPassword()));
        employeeRepository.save(addEmployee);
    }


    /**
     * Метод устанавливает роль EmployeeRole для добавляемого сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    public void setEmployeeRoleToEmployee(Employee newEmployee) {
        List<EmployeeRole> roles = newEmployee.getEmployeeRole();
        roles.get(0).setEmployee(employeeRepository.findByUsername(newEmployee.getUsername()).get());
        employeeRepository.findByUsername(newEmployee.getUsername()).get().setEmployeeRole(roles);
    }

    /**
     * Метод устанавливает роль USER для добавляемого сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    public void setUserRoleToEmployee(Employee newEmployee) {
        List<EmployeeRole> roles = Arrays.asList(new EmployeeRole("ROLE_USER"));
        roles.get(0).setEmployee(employeeRepository.findByUsername(newEmployee.getUsername()).get());
        employeeRepository.findByUsername(newEmployee.getUsername()).get().setEmployeeRole(roles);
    }

    /**
     * Метод добавляет нового пользователя сотрудника Employee в приложение
     *
     * @param
     * @return EmployeeDTO
     * @throws
     */
    public EmployeeDto addNewEmployee(Employee newEmployee) {
        return employeeMapper.mapToEmployeeDto(employeeRepository.findByUsername(newEmployee.getUsername()).get());
    }

}
