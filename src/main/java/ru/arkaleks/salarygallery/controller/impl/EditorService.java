package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.DocumentPdf;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;
import ru.arkaleks.salarygallery.repository.DocumentPdfRepository;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;
import ru.arkaleks.salarygallery.repository.PaySlipRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Service
public class EditorService {

    private final EmployeeRepository employeeRepository;

    private final PaySlipRepository paySlipRepository;

    private final DocumentPdfRepository documentPdfRepository;

    private final EmployeeMapper employeeMapper;

    private final CurrentUserService currentUserService;


    /**
     * Метод находит все расчетные листы Payslips сотрудника Employee
     */
    @Transactional(readOnly = true)
    public List<PaySlipDto> findAllEmployeePaySlips() {
        Employee employee = currentUserService.getLogInEmployee();
        return employeeMapper.mapToPaySlipDtoList(paySlipRepository.findByEmployee(employee));
    }


    /**
     * Метод удаляет расчетный лист Payslip
     */
    @Transactional
    public void deleteEmployeePaySlip(Integer paySlipId) {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlipList = paySlipRepository.findByEmployee(employee);
        List<Integer> paySlipsIdList = new ArrayList<>();
        for (PaySlip paySlip : paySlipList) {
            paySlipsIdList.add(paySlip.getId());
        }
        if (paySlipsIdList.contains(paySlipId)) {
            DocumentPdf doc = paySlipRepository.findById(paySlipId).get().getDocumentPdf();
            if (doc != null) {
                documentPdfRepository.deleteById(doc.getId());
            }
//            int pdfDocumentId = paySlipRepository.findById(paySlipId).get().getDocumentPdf().getId();
//            if (pdfDocumentId != 0) {
//                documentPdfRepository.deleteById(pdfDocumentId);
//            }
            paySlipRepository.deletePaySlipById(paySlipId);
        } else {
            throw new IllegalArgumentException("Расчетного листа с таким номером не существует!!");
        }
    }


    /**
     * Метод добавляет новый расчетный лист PaySlip
     */
    @Transactional
    public EmployeeDto addNewPaySlipToEmployee(PaySlip paySlip) {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Employee employee = employeeRepository.findByUsername(emplName).get();
        List<PaySlip> paySlips = employee.getPaySlips();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == paySlip.getYear() && ps.getMonth().equals(paySlip.getMonth())) {
                throw new IllegalArgumentException("Извините, расчетный лист уже существует!");
            }
        }
        paySlip.setEmployee(employee);
        paySlips.add(paySlip);
        employee.setPaySlips(paySlips);
        employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }
}
