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
@Transactional
@Service
public class EditorService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaySlipRepository paySlipRepository;

    @Autowired
    private DocumentPdfRepository documentPdfRepository;

    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Autowired
    private CurrentUserService currentUserService;


    /**
     * Метод находит все расчетные листы Payslips сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    public List<PaySlipDto> findAllEmployeePaySlips() {
        Employee employee = currentUserService.getLogInEmployee();
        return employeeMapper.mapToPaySlipDtoList(paySlipRepository.findBy(employee));
    }


    /**
     * Метод удаляет расчетный лист Payslip
     *
     * @param
     * @return
     * @throws
     */
    public void deleteEmployeePaySlip(Integer paySlipId) {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlipList = paySlipRepository.findBy(employee);
        List<Integer> paySlipsIdList = new ArrayList<>();
        for (PaySlip paySlip : paySlipList) {
            paySlipsIdList.add(paySlip.getId());
        }
        if (paySlipsIdList.contains(paySlipId)) {
            DocumentPdf doc = paySlipRepository.findById(paySlipId).get().getDocumentPdf();
            if(doc != null) {
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
     *
     * @param
     * @return EmployeeDTO
     * @throws
     */
    public EmployeeDto addNewPaySlipToEmployee(PaySlip paySlip) {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Employee employee = employeeRepository.findByUsername(emplName).get();
        List<PaySlip> paySlips = employee.getPaySlips();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == paySlip.getYear() && ps.getMonth() == paySlip.getMonth()) {
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
