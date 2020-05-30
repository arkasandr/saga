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

    public Employee(int employeeNumber, String surname, String firstName, String middleName, String company, String department, String position) {
        this.employeeNumber = employeeNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.company = company;
        this.department = department;
        this.position = position;
    }

    public Employee(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Employee(@NonNull String username, @NonNull String password, @NonNull String email, List<EmployeeRole> employeeRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private int id;

    @NonNull
    private int employeeNumber;

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

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PaySlip> paySlips;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeRole> employeeRole;

}
