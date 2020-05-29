package ru.arkaleks.salarygallery.controller.dto;

import lombok.Data;

import java.sql.Date;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
public class DocumentPdfDto {

    private int id;

    private String filename;

    private byte[] content;

    private Date created;
}
