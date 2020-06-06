package ru.arkaleks.salarygallery.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.arkaleks.salarygallery.model.Employee;
import ru.arkaleks.salarygallery.model.EmployeeRole;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserDetailsAdapter implements UserDetails {

    private Employee employee;

    public UserDetailsAdapter(Employee employee) {
        this.employee = employee;
    }


    public Integer getId() {
        return employee.getId();
    }


    @Override
    public String getUsername() {
        return employee.getUsername();
    }


    @Override
    public String getPassword() {
        return employee.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (EmployeeRole employeeRole : employee.getEmployeeRole()) {
            authorities.add(new SimpleGrantedAuthority(employeeRole.getRolename()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return employee.getEmail();
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}