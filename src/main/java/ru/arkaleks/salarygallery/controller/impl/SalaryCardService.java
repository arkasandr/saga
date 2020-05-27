package ru.arkaleks.salarygallery.controller.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;
import ru.arkaleks.salarygallery.model.DocumentPdf;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.SalaryCard;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class SalaryCardService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeMapper mapper = EmployeeMapper.INSTANCE;


    public void multipartFileToFile(MultipartFile multipart) throws IOException {
        Path filepath = Paths.get("C:/Projects/saga/src/main/resources/pdf", multipart.getOriginalFilename());
        multipart.transferTo(filepath);
    }

    public  static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+ "/" + multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }


    /**
     * Метод сохраняет нового пользователя User без роли UserRole
     *
     * @param
     * @return
     * @throws
     */

    public void uploadFile(MultipartFile multiFile) throws IOException, ParseException {
        String uploadDir = "C:/Projects/saga/src/main/resources/pdf";
        File file = multipartToFile(multiFile);
          Employee employee = getDataFromPDF(file);
            if (employee.getSurname() != null) {
                System.out.println(employee.getSurname());
                employeeRepository.save(employee);
            } else {
                throw new IllegalArgumentException("Данные не сохранены!");
            }
    }


    /**
     * Метод находит всех пользователей User
     *
     * @param
     * @return List<UserDto>
     * @throws
     */
    public List<EmployeeDto> getAllEmployees() {
        return mapper.mapToEmployeeDtoList(employeeRepository.findAll());
    }

    /**
     * Метод сохраняет нового пользователя User без роли UserRole
     *
     * @param
     * @return
     * @throws
     */
    public EmployeeDto saveNewEmployee(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee emp : employees) {
            if (emp.getId() == newEmployee.getId()) {
                throw new IllegalArgumentException("Sorry, User with id = " + emp.getId() + " is exists!");
            }
        }
        Employee addEmployee = new Employee(newEmployee.getId(), newEmployee.getSurname(), newEmployee.getFirstName(),
                newEmployee.getMiddleName(), newEmployee.getCompany(), newEmployee.getDepartment(), newEmployee.getPosition());
        employeeRepository.save(addEmployee);
        return mapper.mapToEmployeeDto(employeeRepository.findById(newEmployee.getId()).get());
    }



    /**
     * Метод получает данные из файла pdf
     *
     * @param
     * @return Employee
     * @throws IOException, ParseException
     */
    public Employee getDataFromPDF(File file) throws IOException, ParseException {
        Employee result = new Employee();
        try {
        //   File file = new File("C:/projects/salarycards/SalaryCard_05_rom.pdf");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            if (!text.isEmpty()) {
                List<String> lines = getLinesFromText(text);
                int id = Integer.parseInt(lines.get(6).trim());
                long millis = System.currentTimeMillis();
                DocumentPdf doc = new DocumentPdf(id, file.getName(), getByteArrayFromFile(file), new java.sql.Date(millis));
                String surname = StringUtils.substringBefore(lines.get(3), " ").trim();
                String firstName = StringUtils.substringBetween(lines.get(3), " ", " ").trim();
                String middleName = StringUtils.substringAfterLast(lines.get(3), firstName + " ").trim();
                String company = StringUtils.substringAfter(lines.get(0), "Организация ").trim();
                String department = StringUtils.substringAfter(lines.get(2), company + " ").trim();
                String position = lines.get(4).trim();
                List<SalaryCard> salaryCards = new ArrayList<>();
                int year = Integer.parseInt(StringUtils.substringAfterLast(lines.get(1).trim(), " "));
                String month = StringUtils.substringBetween(lines.get(1).trim(), "начисления ", " " + year);
                Double advance = (DecimalFormat.getNumberInstance().parse(StringUtils.substringBetween
                        (StringUtils.substringAfter(lines.get(7).trim(), "банк) "), " ", " "))
                        .doubleValue()) * 1000 +
                        DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast
                                (lines.get(7).trim(), " ")).doubleValue();
                Double salary = (DecimalFormat.getNumberInstance().parse(StringUtils.substringAfter
                        (lines.get(5).trim(), "выплате: ")).doubleValue()) * 1000 +
                        DecimalFormat.getNumberInstance().parse(StringUtils.substringAfterLast
                                (lines.get(5).trim(), " ")).doubleValue();
                SalaryCard salaryCard = new SalaryCard(id, year, month, advance, salary, doc);
                doc.setSalarycard(salaryCard);
                salaryCard.setDocumentPdf(doc);
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
                if (line.contains("Аванс")) {
                    result.add(line);
                }
                line = lineNumberReader.readLine();
            }

            for (String str : result) {
                if (str == null) {
                    throw new NullPointerException("Строка" + str + "имеет нулевой аргумент!");
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return result;
    }


    /**
     * Метод преобразует файл в массив байт
     *
     * @param
     * @return byte[]
     * @throws IOException
     */
    private byte[] getByteArrayFromFile(File file) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream in = new FileInputStream(file);
        final byte[] buffer = new byte[500];
        int read = -1;
        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }
        in.close();
        return baos.toByteArray();
    }

}
