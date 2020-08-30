package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;
import ru.arkaleks.salarygallery.repository.PaySlipRepository;

import java.util.*;
import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Service
public class ChartsService {

    private final PaySlipRepository paySlipRepository;
    private final CurrentUserService currentUserService;
    private final EmployeeMapper employeeMapper;

    /**
     * Метод возвращает все расчетные листы сотрудника
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @Transactional
    public List<PaySlipDto> getAllPaySlipsChart() {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findByEmployee(employee);
        return employeeMapper.mapToPaySlipDtoList(paySlips);
    }

    /**
     * Метод возвращает данные последнего расчетного листа сотрудника
     *
     * @param
     * @return PaySlip
     * @throws
     */
    @Transactional
    public PaySlipDto getLastPaySlipPieChart() {
        PaySlip result = new PaySlip();
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findByEmployee(employee);
        if (paySlips != null && !paySlips.isEmpty()) {
            result = paySlips.get(paySlips.size() - 1);
        }
        return employeeMapper.mapToPaySlipDto(result);
    }

    /**
     * Метод возвращает расчетные листы сотрудника за текущий год
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @Transactional
    public List<PaySlipDto> getRecentYearPaySlipsChart() {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findByEmployee(employee);
        List<PaySlip> currentPaySlips = new ArrayList<>();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                currentPaySlips.add(ps);
            }
        }
        return employeeMapper.mapToPaySlipDtoList(currentPaySlips);
    }

    /**
     * Метод возвращает расчетные листы сотрудника за прошедший год
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @Transactional
    public List<PaySlipDto> getLastYearPaySlipsChart() {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findByEmployee(employee);
        List<PaySlip> currentPaySlips = new ArrayList<>();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == Calendar.getInstance().get(Calendar.YEAR) - 1) {
                currentPaySlips.add(ps);
            }
        }
        return employeeMapper.mapToPaySlipDtoList(currentPaySlips);
    }

    /**
     * Метод возвращает данные для сравнения расчетных листов сотрудника за указанные годы
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @Transactional
    public List<PaySlipDto> getCompareYearsPaySlipsChart(int[] years) {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findByEmployee(employee);
        List<PaySlip> comparePaySlips = new ArrayList<>();
        List<PaySlip> firstYearPaySlips = new ArrayList<>();
        List<PaySlip> secondYearPaySlips = new ArrayList<>();
        Calendar firstCalendar = Calendar.getInstance();
        Calendar secondCalendar = Calendar.getInstance();
        firstCalendar.set(Calendar.YEAR, years[0]);
        secondCalendar.set(Calendar.YEAR, years[1]);
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == firstCalendar.get(Calendar.YEAR)) {
                firstYearPaySlips.add(ps);
            } else if (ps.getYear() == secondCalendar.get(Calendar.YEAR)) {
                secondYearPaySlips.add(ps);
            }
        }
        if (firstYearPaySlips.size() == 0 || secondYearPaySlips.size() == 0) {
            throw new IllegalArgumentException("Извините, данные за один из двух годов отсутствуют!");
        } else {
            comparePaySlips.addAll(firstYearPaySlips);
            comparePaySlips.addAll(secondYearPaySlips);
        }
        return employeeMapper.mapToPaySlipDtoList(comparePaySlips);
    }

}
