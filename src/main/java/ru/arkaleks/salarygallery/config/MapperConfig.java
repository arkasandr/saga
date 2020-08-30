package ru.arkaleks.salarygallery.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.arkaleks.salarygallery.controller.mapper.EmployeeMapper;

@Configuration
public class MapperConfig {
    @Bean
    EmployeeMapper employeeMapper() {
        return Mappers.getMapper(EmployeeMapper.class);
    }

}
