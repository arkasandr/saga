package ru.arkaleks.salarygallery.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.arkaleks.salarygallery.service.PDFReadingClient;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Configuration
public class DataBaseInitConfig {

    @Bean
    CommandLineRunner initDatabase(PDFReadingClient client) {
        return args -> {
            client.getDataFromPDF();

        };
    }
}
