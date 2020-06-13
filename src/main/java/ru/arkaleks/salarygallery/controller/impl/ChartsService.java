package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.PaySlip;
import ru.arkaleks.salarygallery.repository.PaySlipRepository;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class ChartsService {

    @Autowired
    PaySlipRepository paySlipRepository;

    @Autowired
    CurrentUserService currentUserService;

    private final Map<String, java.time.Month> months = new HashMap<>();


    /**
     * Метод формирует массив данных из всех расчетных листов для построения графика
     *
     * @param
     * @return XYDataset
     * @throws ParseException
     */
    public XYDataset createDataset(List<PaySlip> paySlips) throws ParseException {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("За весь период");
        for (PaySlip ps : paySlips) {
            series.add(new Month(parseMonthToInt(ps.getMonth()) + 1, ps.getYear()), ps.getSalary() + ps.getAdvance());
        }
        dataset.addSeries(series);
        return dataset;

    }

    /**
     * Метод преобразует месяц получения расчетного листа в целое число
     *
     * @param
     * @return Integer
     * @throws ParseException
     */
    private int parseMonthToInt(String str) throws ParseException {
        monthToMap();
        Calendar calendar = Calendar.getInstance();
        int newMonth = parseMonth(str).getValue() - 1;
        calendar.set(Calendar.MONTH, newMonth);
        return calendar.get(Calendar.MONTH);
    }


    /**
     * Метод создает график на основе массива данных
     *
     * @param
     * @return JFreeChart
     * @throws
     */
    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Зарплатный график за истекший период",
                "Период",
                "Доход (руб.)",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYSplineRenderer spline = new XYSplineRenderer();
        spline.setSeriesShapesVisible(0, false);
        spline.setSeriesPaint(0, Color.BLUE);
        spline.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderers(new XYItemRenderer[]{spline});

        plot.setBackgroundPaint(Color.GRAY);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        DateAxis timeAxis = (DateAxis) plot.getDomainAxis();
        timeAxis.setDateFormatOverride(new SimpleDateFormat("LLLL.yyyy", new Locale("RU", "ru")));

        timeAxis.setUpperMargin(DateAxis.DEFAULT_UPPER_MARGIN * 2);
        timeAxis.setLowerMargin(DateAxis.DEFAULT_LOWER_MARGIN * 2);


        plot.setDomainGridlinesVisible(true);
        plot.setDomainMinorGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle((String) null);
        chart.removeLegend();

        return chart;
    }


    /**
     * Метод преобразует график из PNG файла в массив байт
     *
     * @param
     * @return byte[]
     * @throws IOException
     */
    public byte[] writePNGFileToStream(JFreeChart chart) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(bos, chart, 600, 400);
        return bos.toByteArray();

    }

    /**
     * Метод создает график всех расчетных листов сотрудника
     *
     * @param
     * @return byte[]
     * @throws IOException
     */
    public byte[] getAllPaySlipsChart() throws ParseException, IOException {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findBy(employee);
        XYDataset dataset = createDataset(paySlips);
        JFreeChart chart = createChart(dataset);
        byte[] bytes = Base64.encodeBase64(writePNGFileToStream(chart));
        return bytes;
    }

    /**
     * Метод создает график расчетных листов сотрудника за текущий год
     *
     * @param
     * @return byte[]
     * @throws IOException
     */
    public byte[] getRecentYearPaySlipsChart() throws ParseException, IOException {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findBy(employee);
        List<PaySlip> currentPaySlips = new ArrayList<>();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                currentPaySlips.add(ps);
            }
        }
        XYDataset dataset = createDataset(currentPaySlips);
        JFreeChart chart = createChart(dataset);
        byte[] bytes = Base64.encodeBase64(writePNGFileToStream(chart));
        return bytes;
    }

    /**
     * Метод создает график расчетных листов сотрудника за прошедший год
     *
     * @param
     * @return byte[]
     * @throws IOException
     */
    public byte[] getLastYearPaySlipsChart() throws ParseException, IOException {
        Employee employee = currentUserService.getLogInEmployee();
        List<PaySlip> paySlips = paySlipRepository.findBy(employee);
        List<PaySlip> currentPaySlips = new ArrayList<>();
        for (PaySlip ps : paySlips) {
            if (ps.getYear() == Calendar.getInstance().get(Calendar.YEAR) - 1) {
                currentPaySlips.add(ps);
            }
        }
        XYDataset dataset = createDataset(currentPaySlips);
        JFreeChart chart = createChart(dataset);
        byte[] bytes = Base64.encodeBase64(writePNGFileToStream(chart));
        return bytes;
    }


    public void monthToMap() {
        this.months.put("январь", java.time.Month.JANUARY);
        this.months.put("февраль", java.time.Month.FEBRUARY);
        this.months.put("март", java.time.Month.MARCH);
        this.months.put("апрель", java.time.Month.APRIL);
        this.months.put("май", java.time.Month.MAY);
        this.months.put("июнь", java.time.Month.JUNE);
        this.months.put("июль", java.time.Month.JULY);
        this.months.put("август", java.time.Month.AUGUST);
        this.months.put("сентябрь", java.time.Month.SEPTEMBER);
        this.months.put("октябрь", java.time.Month.OCTOBER);
        this.months.put("ноябрь", java.time.Month.NOVEMBER);
        this.months.put("декабрь", java.time.Month.DECEMBER);
    }


    public java.time.Month parseMonth(String str) {
        return this.months.get(str);
    }

}
