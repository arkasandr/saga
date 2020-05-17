package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SALARYCARD")
public class SalaryCard {

    public SalaryCard(int year, String month, double advance, double salary) {
        this.year = year;
        this.month = month;
        this.advance = advance;
        this.salary = salary;
    }

    @Id
    @Column(name = "SALARYCARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int year;

    private String month;

    private double advance;

    private double salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

}
