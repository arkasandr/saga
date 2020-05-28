package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    public Employee(int id, String surname, String firstName, String middleName, String company, String department, String position) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.company = company;
        this.department = department;
        this.position = position;
    }

    @Id
    @Column(name = "EMPLOYEE_ID")
    private int id;

    @NonNull
    private String surname;

    @NonNull
    private String firstName;

    @NonNull
    private String middleName;

    @NonNull
    private String company;

    @NonNull
    private String department;

    @NonNull
    private String position;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PaySlip> paySlips;

}
