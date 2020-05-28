package ru.arkaleks.salarygallery.controller.dto;

import lombok.Data;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
public class PaySlipDto {

    private int id;

    private int year;

    private String month;

    private double advance;

    private double salary;

}
