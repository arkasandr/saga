package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.DocumentPdf;
import ru.arkaleks.salarygallery.model.PaySlip;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

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
@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class PaySlipService {

    private final CurrentUserService currentUserService;

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;


    /**
     * Метод преобразует MultipartFile в File
     */
    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File("C:/Projects/saga/src/main/resources/pdf" + "/"
                + multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }


    /**
     * Метод сохраняет PDF файл и добавляет днные из него к зарегистрированному сотруднику
     */
    public void uploadFile(MultipartFile multiFile) throws IOException, ParseException {
 //       String uploadDir = "C:/Projects/saga/src/main/resources/pdf";
        File file = multipartToFile(multiFile);
        getDataFromPDF(file);
    }


    /**
     * Метод получает данные из файла pdf
     */
    public void getDataFromPDF(File file) throws ParseException {
        String username = currentUserService.getCurrentEmployee().getUsername();
        String password = currentUserService.getCurrentEmployee().getPassword();
        String email = currentUserService.getCurrentEmployee().getEmail();
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            if (!text.isEmpty()) {
                List<String> lines = getLinesFromText(text);
                int employeeNumber = Integer.parseInt(lines.get(6).trim());
                long millis = System.currentTimeMillis();
                DocumentPdf doc = new DocumentPdf(employeeNumber, file.getName(), getByteArrayFromFile(file),
                        new java.sql.Date(millis));
                String surname = StringUtils.substringBefore(lines.get(3), " ").trim();
                String firstName = StringUtils.substringBetween(lines.get(3), " ", " ").trim();
                String middleName = StringUtils.substringAfterLast(lines.get(3), firstName + " ").trim();
                String company = StringUtils.substringAfter(lines.get(0), "Организация ").trim();
                String department = StringUtils.substringAfter(lines.get(2), company + " ").trim();
                String position = lines.get(4).trim();
                List<PaySlip> paySlips = new ArrayList<>();
                int year = Integer.parseInt(StringUtils.substringAfterLast(lines.get(1).trim(), " "));
                String month = StringUtils.substringBetween(lines.get(1).trim(), "начисления ", " " + year);
                Double advance = (DecimalFormat.getNumberInstance().parse(StringUtils.substringBetween(
                        StringUtils.substringAfter(lines.get(7).trim(), "банк) "), " ", " "))
                        .doubleValue()) * 1000
                        + DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast(
                                lines.get(7).trim(), " ")).doubleValue();
                Double salary = (DecimalFormat.getNumberInstance().parse(StringUtils.substringAfter(
                        lines.get(5).trim(), "выплате: ")).doubleValue()) * 1000
                        + DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast(
                                lines.get(5).trim(), " ")).doubleValue();

                PaySlip paySlip = new PaySlip(year, month, advance, salary, doc);
                doc.setPaySlip(paySlip);
                paySlip.setDocumentPdf(doc);
                employeeMapper.mapToEmployeeDto(employeeRepository.findByUsername(username)
                        .map(x -> {
                            x.setEmployeeNumber(employeeNumber);
                            x.setSurname(surname);
                            x.setFirstName(firstName);
                            x.setMiddleName(middleName);
                            x.setCompany(company);
                            x.setDepartment(department);
                            x.setPosition(position);
                            paySlip.setEmployee(x);
                            paySlips.add(paySlip);
                            x.setPaySlips(paySlips);
                            x.setUsername(username);
                            x.setPassword(password);
                            x.setEmail(email);
                            return employeeRepository.save(x);
                        })
                        .orElseGet(() -> {
                            throw new IllegalArgumentException("Извините, имя пользователя уже существует!");
                        }));
                document.close();
            } else {
                log.warn("Расчетный лист пустой!");
            }
        } catch (IOException ioex) {
            log.warn("Расчетный лист не найден!", ioex);
        }
    }


    /**
     * Метод находит строки в тексте по номеру
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
                if (line.contains("Аванс")) {
                    result.add(line);
                }
                line = lineNumberReader.readLine();
            }

            for (String str : result) {
                if (str == null) {
                    throw new NullPointerException("Строка имеет нулевой аргумент!");
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return result;
    }


    /**
     * Метод преобразует файл в массив байт
     */
    private byte[] getByteArrayFromFile(File file) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream in = new FileInputStream(file);
        final byte[] buffer = new byte[500];
        int read;
        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }
        in.close();
        return baos.toByteArray();
    }

}
