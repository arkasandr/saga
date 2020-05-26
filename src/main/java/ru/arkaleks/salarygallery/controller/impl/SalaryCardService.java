package ru.arkaleks.salarygallery.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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

    public ResponseEntity<Object> uploadFile(MultipartFile file) throws IOException {
        String uploadDir = "C:/projects/salarygallery/src/main/resources/pdf";
//        if(!file.getOriginalFilename().isEmpty()) {
//            BufferedOutputStream outputStream = new BufferedOutputStream(
//                    new FileOutputStream(
//                            new File(uploadDir, file.getOriginalFilename())));
//            outputStream.write(file.getBytes());
//            outputStream.flush();
//            outputStream.close();
//        }
        Path path = Paths.get(uploadDir, file.getOriginalFilename());
        Files.write(path, file.getBytes());
        return new ResponseEntity<>("File Uploaded Successfully.", HttpStatus.OK);
    }


    /**
     * Метод находит всех пользователей User
     *
     * @param
     * @return List<UserDto>
     * @throws
     */
    public List<EmployeeDto> getAllEmployees() {
        return mapper.mapToEmployeeDtoList(employeeRepository.findAll());
    }

    /**
     * Метод сохраняет нового пользователя User без роли UserRole
     *
     * @param
     * @return
     * @throws
     */
    public EmployeeDto saveNewEmployee(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee emp : employees) {
            if (emp.getId() == newEmployee.getId()) {
                throw new IllegalArgumentException("Sorry, User with id = " + emp.getId() + " is exists!");
            }
        }
        Employee addEmployee = new Employee(newEmployee.getId(), newEmployee.getSurname(), newEmployee.getFirstName(),
                newEmployee.getMiddleName(), newEmployee.getCompany(), newEmployee.getDepartment(), newEmployee.getPosition());
        employeeRepository.save(addEmployee);
        return mapper.mapToEmployeeDto(employeeRepository.findById(newEmployee.getId()).get());
    }

}
