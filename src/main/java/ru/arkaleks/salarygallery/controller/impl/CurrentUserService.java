package ru.arkaleks.salarygallery.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.salarygallery.service.UserDetailsAdapter;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@RestController
@Transactional
public class CurrentUserService {

    /**
     * Метод обновляет данные сотрудника Employee
     *
     * @param
     * @return EmployeeDTO
     * @throws
     */
    public UserDetailsAdapter getCurrentEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter currentEmployee = (UserDetailsAdapter) auth.getPrincipal();
        return currentEmployee;
    }
}
