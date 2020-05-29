package ru.arkaleks.salarygallery.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEEROLE")
public class EmployeeRole implements GrantedAuthority {

    public EmployeeRole(String rolename) {
        this.rolename = rolename;
    }

    @Id
    @Column(name = "EMPLOYEEROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String rolename;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    public int getId() {
        return id;
    }

    public String getRolename() {
        return rolename;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }
}
