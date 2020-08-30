package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.dto.EmployeeDto;
import ru.arkaleks.salarygallery.controller.impl.PaySlipService;
import ru.arkaleks.salarygallery.model.Employee;

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
public class PaySlipController {

    private final PaySlipService paySlipService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/registrationend/uploadfile")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
            paySlipService.uploadFile(file);
    }
}
