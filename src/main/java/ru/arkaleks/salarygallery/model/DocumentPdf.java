package ru.arkaleks.salarygallery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENTS")
public class DocumentPdf {

    public DocumentPdf(int id, String filename, byte[] content, Date created) {
        this.id = id;
        this.filename = filename;
        this.content = content;
        this.created = created;
    }

    @Id
    @Column(name = "DOCUMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FILENAME")
    private String filename;

    @Lob
    private byte[] content;

    private Date created;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SALARYCARD_ID")
    private SalaryCard salarycard;
}
