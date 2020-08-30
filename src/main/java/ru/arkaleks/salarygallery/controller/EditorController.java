package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
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
public class EditorController {

    private final EditorService editorService;

    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     */
    @Transactional(readOnly = true)
    @GetMapping("/editor/payslip/all")
    List<PaySlipDto> findAllEmployeePayslips() {
        return editorService.findAllEmployeePaySlips();
    }

    /**
     * Метод удаляет расчетный лист Payslip сотрудника Employee
     */
    @Transactional
    @DeleteMapping("/editor/payslip/{paySlipId}/delete")
    void deleteEmployeePayslip(@PathVariable int paySlipId) {
        editorService.deleteEmployeePaySlip(paySlipId);
    }


    /**
     * Метод добавляет новый расчетный лист
     */
    @Transactional
    @PostMapping("/editor/payslip/add")
    EmployeeDto addNewPaySlip(@RequestBody PaySlip paySlip) {
        return editorService.addNewPaySlipToEmployee(paySlip);
    }

}
