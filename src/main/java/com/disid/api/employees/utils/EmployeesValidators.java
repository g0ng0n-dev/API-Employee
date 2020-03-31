package com.disid.api.employees.utils;

import com.disid.api.departments.models.Department;
import com.disid.api.employees.controllers.httpRequests.EmployeeHttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class EmployeesValidators {

    public static void validateEmployeeHttpRequest(EmployeeHttpRequest employeeHttpRequest){

        if (StringUtils.isEmpty(employeeHttpRequest.getFirstName())){
            throw new IllegalArgumentException("The First Name field is mandatory");
        }

        if (StringUtils.isEmpty(employeeHttpRequest.getLastName())){
            throw new IllegalArgumentException("The Last Name field is mandatory");
        }

        if (employeeHttpRequest.getAge() == null ){
            throw new IllegalArgumentException("The Age field is mandatory");
        }

        if (employeeHttpRequest.getDepartmentId() == null){
            throw new IllegalArgumentException("The department id field is mandatory");
        }
    }

    public static Department validateDepartment(Optional<Department> departmentOptional){

        if(! departmentOptional.isPresent()){
            throw new IllegalArgumentException("The Department provided was not found, please change the department");
        }

        return departmentOptional.get();

    }
}
