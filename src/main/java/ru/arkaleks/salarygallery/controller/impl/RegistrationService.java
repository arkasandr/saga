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

    @Autowired
    private CurrentUserService currentUserService;

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


    /**
     * Метод обновляет данные сотрудника Employee
     *
     * @param
     * @return EmployeeDTO
     * @throws
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
