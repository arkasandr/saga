package ru.arkaleks.salarygallery.controller.dto;

import lombok.Data;
import ru.arkaleks.salarygallery.model.SalaryCard;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
public class EmployeeDto {

    private int id;

    private String surname;

    private String firstName;

    private String middleName;

    private String company;

    private String department;

    private String position;

    private List<SalaryCard> salaryCards;
}
