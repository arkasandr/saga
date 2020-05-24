package ru.arkaleks.salarygallery.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;
import ru.arkaleks.salarygallery.service.PdfParseClient;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Configuration
public class DataBaseInitConfig {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, PdfParseClient client) {
        return args -> {
            Employee employee = client.getDataFromPDF();
            if (employee.getSurname() != null) {
                System.out.println(employee.getSurname());
                repository.save(employee);
            } else {
                throw new IllegalArgumentException("Данные не сохранены!");
            }
        };
    }
}
