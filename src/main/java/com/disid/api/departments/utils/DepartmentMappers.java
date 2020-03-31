package com.disid.api.departments.utils;

import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.departments.models.Department;

public class DepartmentMappers {


    public static DepartmentHttpResponse mapToHttpResponse(Department newDepartment) {

        DepartmentHttpResponse newDepartmentHttpResponse = new DepartmentHttpResponse();
        newDepartmentHttpResponse.setName(newDepartment.getName());
        newDepartmentHttpResponse.setId(newDepartment.getId());

        return newDepartmentHttpResponse;
    }
}
