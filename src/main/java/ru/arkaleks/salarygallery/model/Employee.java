package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String surname;

    private String firstName;

    private String middleName;

    private String company;

    private String department;

    private String position;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SalaryCard> salaryCards;


}
