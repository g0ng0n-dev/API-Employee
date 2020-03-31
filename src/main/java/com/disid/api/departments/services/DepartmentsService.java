package com.disid.api.departments.services;

import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.departments.models.Department;
import com.disid.api.departments.repositories.DepartmentsRepository;
import com.disid.api.departments.utils.DepartmentMappers;
import com.disid.api.departments.utils.DepartmentValidators;

public class DepartmentsService {

    private DepartmentsRepository departmentsRepository;

    public DepartmentsService(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    public DepartmentHttpResponse createDepartment(CreateDepartmentHttpRequest request) {

        DepartmentValidators.checkIfNameIsValid(request.getName());

        if(departmentsRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("The department already exists");
        }

        Department newDepartment = new Department(request.getName());

        return DepartmentMappers.mapToHttpResponse(departmentsRepository.save(newDepartment));
    }
}
