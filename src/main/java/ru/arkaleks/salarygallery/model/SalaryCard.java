package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SALARYCARD",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "month", "employee_id"})})
public class SalaryCard {

    public SalaryCard(int id, int year, String month, double advance, double salary) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.advance = advance;
        this.salary = salary;
    }

    public SalaryCard(int id, int year, String month, double advance, double salary, DocumentPdf documentPdf) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.advance = advance;
        this.salary = salary;
        this.documentPdf = documentPdf;
    }

    @Id
    @Column(name = "SALARYCARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "YEAR")
    @NonNull
    private int year;

    @Column(name = "MONTH")
    @NonNull
    private String month;

    @NonNull
    private double advance;

    @NonNull
    private double salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToOne(mappedBy = "salarycard", cascade = CascadeType.ALL)
    private DocumentPdf documentPdf;

}
