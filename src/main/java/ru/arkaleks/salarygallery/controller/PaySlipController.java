package ru.arkaleks.salarygallery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.salarygallery.controller.impl.PaySlipService;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
public class PaySlipController {

    private final PaySlipService paySlipService;

    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/registrationend/uploadfile")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        paySlipService.uploadFile(file);
    }
}
