package ru.arkaleks.salarygallery.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.SalaryCard;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Service
@Transactional
public class PdfParseClient {

    /**
     * Метод получает данные из файла pdf
     *
     * @param
     * @return Employee
     * @throws IOException, ParseException
     */

    public Employee getDataFromPDF() throws ParseException {
        Employee result = new Employee();
        try {
            PDDocument document = PDDocument.load(new File("C:/salary.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            if (!text.isEmpty()) {
                int id = Integer.parseInt(getLinesFromText(text).get(6));
                String surname = StringUtils.substringBefore(getLinesFromText(text).get(3), " ");
                String firstName = StringUtils.substringBetween(getLinesFromText(text).get(3), " ", " ");
                String middleName = StringUtils.substringAfterLast(getLinesFromText(text).get(3), " ");
                String company = StringUtils.substringAfter(getLinesFromText(text).get(0), "Организация ");
                String department = StringUtils.substringAfter(getLinesFromText(text).get(2), company + " ");
                String position = getLinesFromText(text).get(4);
                List<SalaryCard> salaryCards = new ArrayList<>();
                int year = Integer.parseInt(StringUtils.substringAfterLast
                        (getLinesFromText(text).get(1), " "));
                String month = StringUtils.substringBetween
                        (getLinesFromText(text).get(1), "начисления ", " " + year);
                Double advance = (DecimalFormat.getNumberInstance().parse(StringUtils.substringBetween
                        (StringUtils.substringAfter(getLinesFromText(text).get(7), "банк) "), " ", " "))
                        .doubleValue()) * 1000 +
                        DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast
                                (getLinesFromText(text).get(7), " ")).doubleValue();
                Double salary = (DecimalFormat.getNumberInstance().parse(StringUtils.substringAfter
                        (getLinesFromText(text).get(5), "выплате: ")).doubleValue()) * 1000 +
                        DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast
                                (getLinesFromText(text).get(5), " ")).doubleValue();
                SalaryCard salaryCard = new SalaryCard(id, year, month, advance, salary);
                result.setId(id);
                result.setSurname(surname);
                result.setFirstName(firstName);
                result.setMiddleName(middleName);
                result.setCompany(company);
                result.setDepartment(department);
                result.setPosition(position);
                salaryCard.setEmployee(result);
                salaryCards.add(salaryCard);
                result.setSalaryCards(salaryCards);
                document.close();
            } else {
                System.out.println("Расчетный лист пустой!");
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
            System.out.println("Расчетный лист не найден!");
        }
        return result;
    }

    /**
     * Метод находит строки в тексте по номеру
     *
     * @param
     * @return List<String>
     * @throws IOException
     */

    public List<String> getLinesFromText(String text) {
        List<String> result = new ArrayList<>();
        Reader reader = new StringReader(text);
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            String line = lineNumberReader.readLine();
            while (line != null) {
                int lineNumber = lineNumberReader.getLineNumber();
                if (lineNumber == 1) {
                    result.add(line);
                }
                if (lineNumber == 2) {
                    result.add(line);
                }
                if (lineNumber == 14) {
                    result.add(line);
                }
                if (lineNumber == 15) {
                    result.add(line);
                }
                if (lineNumber == 16) {
                    result.add(line);
                }
                if (lineNumber == 17) {
                    result.add(line);
                }
                if (lineNumber == 18) {
                    result.add(line);
                }
                if (line.startsWith("Аванс")) {
                    result.add(line);
                }
                line = lineNumberReader.readLine();
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return result;
    }
}
