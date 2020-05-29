package ru.arkaleks.salarygallery.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.arkaleks.salarygallery.model.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

    public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

        public CustomLoginFilter(String defaultFilterProcessesUrl) {
            super(defaultFilterProcessesUrl);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpresponse)
                throws AuthenticationException, IOException {
            ObjectMapper mapper = new ObjectMapper();
            Employee employee = mapper.readValue(httpRequest.getReader().lines()
                    .collect(Collectors.joining()), Employee.class);
            Authentication auth = new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Username is " + auth.getPrincipal());
            System.out.println("password is " + auth.getCredentials());
            System.out.println("Filter: URL called: " + httpRequest.getRequestURL().toString());
            return getAuthenticationManager().authenticate(auth);
        }
    }


