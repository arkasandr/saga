package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.AccountService;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     *
     * @param
     * @return PaySlipDto
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/payslip/all")
    List<PaySlipDto> findAllEmployeePayslips() {
        return accountService.findAllEmployeePaySlips();
    }

    /**
     * Метод удаляет расчетный лист Payslip сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    @ResponseBody
    @DeleteMapping("/account/payslip/{paySlipId}/delete")
    void deleteEmployeePayslip(@PathVariable int paySlipId) {
        accountService.deleteEmployeePaySlip(paySlipId);
    }


    /**
     * Метод добавляет новый расчетный лист
     *
     * @param
     * @return
     * @throws
     */
    @ResponseBody
    @PostMapping("/account/payslip/add")
    EmployeeDto addNewPaySlip(@RequestBody PaySlip paySlip) {
        return accountService.addNewPaySlipToEmployee(paySlip);
    }

}
