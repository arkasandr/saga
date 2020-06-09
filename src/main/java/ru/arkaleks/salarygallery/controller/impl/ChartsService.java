package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.model.PaySlip;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.io.File;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class ChartsService {

    public XYDataset createDataset(List<PaySlip> paySlips) throws ParseException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Chart");
        for(PaySlip ps : paySlips) {
            series.add(parseMonthToInt(ps.getMonth()), ps.getSalary()+ps.getAdvance());
        }
        dataset.addSeries(series);
        return dataset;

    }

    private int parseMonthToInt(String str) throws ParseException {
        Date date = new SimpleDateFormat("MMMM").parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Зарплатный график за истекший период",
                "Месяц",
                "Сумма (рубю)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer render = new XYLineAndShapeRenderer();
        render.setSeriesPaint(0, Color.RED);
        render.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(render);
        plot.setBackgroundPaint(Color.WHITE);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainMinorGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Доход", new Font("Serif", Font.BOLD, 16)));
        return chart;
    }

    public void writePNGFile(JFreeChart chart) throws IOException {
        File file = new File("C:/Projects/saga/src/main/resources/png/01.png");
        try(FileOutputStream fop = new FileOutputStream(file)) {
            if(!file.exists()) {
                file.createNewFile();
            }
            ChartUtilities.writeChartAsPNG(fop,chart, 500, 350);

        }
    }

    public void createChartAsPNGFile(List<PaySlip> paySlips) throws ParseException, IOException {
        XYDataset dataset = createDataset(paySlips);
        JFreeChart chart = createChart(dataset);
        writePNGFile(chart);
    }

}
