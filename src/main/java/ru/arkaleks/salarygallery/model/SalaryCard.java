package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SALARYCARD",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "month"})})
public class SalaryCard {

    public SalaryCard(int id, int year, String month, double advance, double salary) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.advance = advance;
        this.salary = salary;
    }

    @Id
    @Column(name = "SALARYCARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "MONTH")
    private String month;

    private double advance;

    private double salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

}
