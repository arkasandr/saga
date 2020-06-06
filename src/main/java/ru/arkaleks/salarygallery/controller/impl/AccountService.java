package ru.arkaleks.salarygallery.controller.impl;


        import lombok.RequiredArgsConstructor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
        import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
        import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
        import ru.arkaleks.salarygallery.model.Employee;
        import ru.arkaleks.salarygallery.model.PaySlip;
        import ru.arkaleks.salarygallery.repository.DocumentPdfRepository;
        import ru.arkaleks.salarygallery.repository.EmployeeRepository;
        import ru.arkaleks.salarygallery.repository.PaySlipRepository;

        import java.io.IOException;
        import java.util.ArrayList;
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
public class AccountService {

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
//    public List<PaySlipDto> findAllEmployeePayslips() {
//        String username = currentUserService.getCurrentEmployee().getUsername();
//        return employeeMapper.mapToPaySlipDtoList(employeeRepository
//                .findByUsername(username).get().getPaySlips());
//    }
    public List<PaySlipDto> findAllEmployeePaySlips() {
        Employee employee = currentUserService.getLogInEmployee();
        return employeeMapper.mapToPaySlipDtoList(paySlipRepository.findBy(employee));
    }


    /**
     * Метод удаляет расчетный лист Payslip сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws IOException
     */
    public void deleteEmployeePaySlip(Integer paySlipId) {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlipList = paySlipRepository.findBy(employee);
        List<Integer> paySlipIdList = new ArrayList<>();
        for (PaySlip paySlip : paySlipList) {
            paySlipIdList.add(paySlip.getId());
        }
        if (paySlipIdList.contains(paySlipId)) {
            int pdfDocumentId = paySlipRepository.findById(paySlipId).get().getDocumentPdf().getId();
            if (pdfDocumentId != 0) {
                documentPdfRepository.deleteById(pdfDocumentId);
            }
            paySlipRepository.deleteById(paySlipId);
        } else {
            throw new IllegalArgumentException("Уникальный номер расчетного листа задан неверно!");
        }
    }


    /**
     * Метод добавляет новый расчетный лист PaySlip
     *
     * @param
     * @return PaySlipDTO
     * @throws
     */
//    public EmployeeDto addNewPaySlipToEmployee(PaySlip paySlip) {
//        Employee employee = currentUserService.getLogInEmployee();
//        List<PaySlip> paySlips = employee.getPaySlips();
//        for (PaySlip ps : paySlips) {
//            if (ps.getYear() == paySlip.getYear() && ps.getMonth() == paySlip.getMonth()) {
//                throw new IllegalArgumentException("Извините, расчетный лист уже существует!");
//            }
//        }
//            paySlip.setEmployee(employee);
//            paySlips.add(paySlip);
//            employee.setPaySlips(paySlips);
//            employeeRepository.save(employee);
//        return employeeMapper.mapToEmployeeDto(employee);
//    }

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
        currentUserService.setLogInEmployee(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }
}
