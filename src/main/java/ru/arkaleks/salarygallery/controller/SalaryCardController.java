package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.impl.SalaryCardService;
import ru.arkaleks.salarygallery.model.Employee;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class SalaryCardController {

private final SalaryCardService salaryCardService;

    /**
     * Метод загружает файл
     *
     * @param
     * @return
     * @throws
     */
    @ResponseBody
    @PostMapping("/lkemployee/uploadfile")
    void uploadFile(MultipartFile file) {
        try {
            salaryCardService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод находит всех пользователей User
     *
     * @param
     * @return List<User>
     * @throws
     */
    @GetMapping("/lkemployee/all")
    public List<EmployeeDto> getAllUsers() {
        return salaryCardService.getAllEmployees();
    }


    /**
     * Метод сохраняет нового пользователя User
     *
     * @param
     * @return UserDTO
     * @throws
     */
    @ResponseBody
    @PostMapping("/lkemployee/addnew")
    EmployeeDto addNewUser(@RequestBody Employee newEmployee) {
       return salaryCardService.saveNewEmployee(newEmployee);

    }


}
