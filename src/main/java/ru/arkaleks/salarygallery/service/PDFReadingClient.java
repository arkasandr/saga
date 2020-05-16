package ru.arkaleks.salarygallery.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Service
@Transactional
public class PDFReadingClient {

    /**
     * Метод осуществляет получение данных из файла pdf
     *
     * @param
     * @return
     * @throws IOException
     */

    public void getDataFromPDF() throws IOException {

        File file = new File("C:/salary.pdf");
        PDDocument document = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        String salary = StringUtils.substringAfter(text, "Долг за предприятием на конец месяца");
        String companyName = StringUtils.substringBetween(text, "Организация:", "Группа");
        String numberOnly = salary.replaceAll("[^0-9]", "");
        System.out.println(companyName);

        //Closing the document
        document.close();

//        tStripper = new PDFTextStripper();
//        tStripper.setStartPage(1);
//        tStripper.setEndPage(3);
//        PDDocument document = PDDocument.load(new File("name.pdf"));
//        document.getClass();
//        if (!document.isEncrypted()) {
//            pdfFileInText = tStripper.getText(document);
//            lines = pdfFileInText.split("\\r\\n\\r\\n");
//            for (String line : lines) {
//                System.out.println(line);
//                content += line;
//            }
//        }
//        System.out.println(content.trim());
//    }
    }
}
