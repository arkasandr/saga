package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.controller.impl.ChartsService;

import java.io.IOException;
import java.text.ParseException;


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
    ResponseEntity<byte[]> getChartForAllPaySlips() throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getAllPaySlipsChart());
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
    ResponseEntity<byte[]> getRecentYearChartForPaySlips() throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getRecentYearPaySlipsChart());
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
    ResponseEntity<byte[]> getLastYearChartForPaySlips() throws IOException, ParseException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(chartsService.getLastYearPaySlipsChart());
    }

}
