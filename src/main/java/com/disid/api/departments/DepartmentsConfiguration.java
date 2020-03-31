package com.disid.api.departments;

import com.disid.api.departments.repositories.DepartmentsRepository;
import com.disid.api.departments.services.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentsConfiguration {

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Bean
    public DepartmentsService departmentsService() {
        return new DepartmentsService(departmentsRepository);
    }

}
