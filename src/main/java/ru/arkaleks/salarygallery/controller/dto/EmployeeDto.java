package ru.arkaleks.salarygallery.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.arkaleks.salarygallery.model.EmployeeRole;
import ru.arkaleks.salarygallery.model.PaySlip;

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

    private String username;

    private String password;

    private String email;

    @JsonIgnore
    private List<PaySlip> paySlips;

    @JsonIgnore
    private List<EmployeeRole> employeeRole;

}
