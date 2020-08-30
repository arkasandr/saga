package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.EmployeeRole;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Transactional
@Service
public class RegistrationService {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CurrentUserService currentUserService;

    private final EmployeeMapper employeeMapper;

    /**
     * Метод сохраняет нового сотрудника Employee без роли EmployeeRole
     */
    public void saveEmployeeWithoutEmployeeRole(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getUsername() != null) {
                if (employee.getUsername().equals(newEmployee.getUsername())) {
                    throw new IllegalArgumentException("Извините, имя пользователя \" = " + employee.getUsername() + " \" уже существует!");
                }
            } else {
                throw new IllegalArgumentException("Извините, имя пользователя не задано!");
            }
        }
        Employee addEmployee = new Employee(newEmployee.getUsername(), newEmployee.getPassword(), newEmployee.getEmail());
        addEmployee.setPassword(bCryptPasswordEncoder.encode(newEmployee.getPassword()));
        employeeRepository.save(addEmployee);
    }


    /**
     * Метод устанавливает роль USER для добавляемого сотрудника Employee
     */
    public void setUserRoleToEmployee(Employee newEmployee) {
        List<EmployeeRole> roles = Collections.singletonList(new EmployeeRole("ROLE_USER"));
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(newEmployee.getUsername());
        Employee employee = optionalEmployee.orElseGet(Employee::new);
        roles.get(0).setEmployee(employee);
    }

    /**
     * Метод добавляет нового пользователя сотрудника Employee в приложение
     */
    public EmployeeDto addNewEmployee(Employee newEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(newEmployee.getUsername());
        Employee employee = optionalEmployee.orElseGet(Employee::new);
        return employeeMapper.mapToEmployeeDto(employee);
    }


    /**
     * Метод обновляет данные сотрудника Employee
     */
    public EmployeeDto updateEmployeeByUsername(Employee employee) {
        String username = currentUserService.getCurrentEmployee().getUsername();
        return employeeMapper.mapToEmployeeDto(employeeRepository
                .findByUsername(username)
                .map(x -> {
                    x.setEmployeeNumber(employee.getEmployeeNumber());
                    x.setSurname(employee.getSurname());
                    x.setFirstName(employee.getFirstName());
                    x.setMiddleName(employee.getMiddleName());
                    x.setCompany(employee.getCompany());
                    x.setDepartment(employee.getDepartment());
                    x.setPosition(employee.getPosition());
                    return employeeRepository.save(x);
                })
                .orElseGet(() -> {
                    throw new IllegalArgumentException("Извините, имя пользователя \" " + employee.getUsername() + " \" не существует!");
                }));
    }

}
