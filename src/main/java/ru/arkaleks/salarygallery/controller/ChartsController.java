package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.ChartsService;

import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class ChartsController {

    @Autowired
    ChartsService chartsService;


    /**
     * Метод строит график всех расчетных листов Payslip сотрудника Employee
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/all")
    List<PaySlipDto> getChartForAllPaySlips() {
        return chartsService.getAllPaySlipsChart();
    }

    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за текущий год
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/recentyear")
    List<PaySlipDto> getRecentYearChartForPaySlips() {
        return chartsService.getRecentYearPaySlipsChart();
    }

    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за прошедший год
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/lastyear")
    List<PaySlipDto> getLastYearChartForPaySlips() {
        return chartsService.getLastYearPaySlipsChart();
    }

    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за сравниваемые годы
     *
     * @param
     * @return List<PaySlipDto>
     * @throws
     */
    @ResponseBody
    @PostMapping("/account/chart/compare")
    List<PaySlipDto> getCompareYearsChartForPaySlips(@RequestBody int[] years) {
        return chartsService.getCompareYearsPaySlipsChart(years);
    }


    /**
     * Метод находит все расчетные листы Payslip сотрудника Employee
     *
     * @param
     * @return PaySlipDto
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/lastpie")
    PaySlipDto findLastEmployeePayslip() {
        return chartsService.getLastPaySlipPieChart();
    }

}
