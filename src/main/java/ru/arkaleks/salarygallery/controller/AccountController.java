package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.AccountService;

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
     * @return EmployeeDto
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/payslip/all")
    List<PaySlipDto> findAllEmployeePayslips() {
        return accountService.findAllEmployeePayslips();
    }


}
