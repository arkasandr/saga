package ru.arkaleks.salarygallery.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.arkaleks.salarygallery.repository.EmployeeRepository;


@Service
public class UserService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public UserService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("Can't found username \'" + username + "\'"));
    }

}
