package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.EditorService;
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
public class EditorController {

    @Autowired
    private EditorService editorService;

    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @GetMapping("/editor/payslip/all")
    List<PaySlipDto> findAllEmployeePayslips() {
        return editorService.findAllEmployeePaySlips();
    }

    /**
     * Метод удаляет расчетный лист Payslip сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    @ResponseBody
    @DeleteMapping("/editor/payslip/{paySlipId}/delete")
    void deleteEmployeePayslip(@PathVariable int paySlipId) {
        editorService.deleteEmployeePaySlip(paySlipId);
    }


    /**
     * Метод добавляет новый расчетный лист
     *
     * @param
     * @return EmployeeDto
     * @throws
     */
    @ResponseBody
    @PostMapping("/editor/payslip/add")
    EmployeeDto addNewPaySlip(@RequestBody PaySlip paySlip) {
        return editorService.addNewPaySlipToEmployee(paySlip);
    }

}
