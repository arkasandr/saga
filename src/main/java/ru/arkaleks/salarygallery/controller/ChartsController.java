package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.salarygallery.controller.dto.PaySlipDto;
import ru.arkaleks.salarygallery.controller.impl.ChartsService;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.io.IOException;
import java.text.ParseException;
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
     * @return byte[]
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/all")
    ResponseEntity<byte[]> getChartForAllPaySlips() throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getAllPaySlipsChart());
    }

//    /**
//     * Метод строит график расчетных листов Payslip сотрудника Employee за текущий год
//     *
//     * @param
//     * @return Lbyte[]
//     * @throws
//     */
//    @ResponseBody
//    @GetMapping("/account/chart/recentyear")
//    ResponseEntity<byte[]> getRecentYearChartForPaySlips() throws IOException, ParseException {
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.IMAGE_PNG)
//                .body(chartsService.getRecentYearPaySlipsChart());
//    }

    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за текущий год
     *
     * @param
     * @return Lbyte[]
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/recentyear")
    List<PaySlipDto> getRecentYearChartForPaySlips() throws IOException, ParseException {
        return chartsService.getRecentYearPaySlipsChart();
    }
    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за прошедший год
     *
     * @param
     * @return byte[]
     * @throws
     */
    @ResponseBody
    @GetMapping("/account/chart/lastyear")
    ResponseEntity<byte[]> getLastYearChartForPaySlips() throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getLastYearPaySlipsChart());
    }

    /**
     * Метод строит график расчетных листов Payslip сотрудника Employee за сравниваемые годы
     *
     * @param
     * @return byte[]
     * @throws
     */
    @ResponseBody
    @PostMapping("/account/chart/compare")
    ResponseEntity<byte[]> getCompareYearsChartForPaySlips(@RequestBody int[] years) throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getCompareYearsPaySlipsChart(years));
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
