package com.disid.api.employees.utils;

import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.departments.models.Department;
import com.disid.api.employees.controllers.httpResponses.EmployeeHttpResponse;
import com.disid.api.employees.models.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeesMappers {

    public static EmployeeHttpResponse mapToHttpResponse(Employee employee) {

        DepartmentHttpResponse departmentHttpResponse = new DepartmentHttpResponse();
        departmentHttpResponse.setId(employee.getDepartment().getId());
        departmentHttpResponse.setName(employee.getDepartment().getName());

        EmployeeHttpResponse newEmployee = new EmployeeHttpResponse();
        newEmployee.setId(employee.getId());
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setDepartmentHttpResponse(departmentHttpResponse);
        newEmployee.setAge(employee.getAge());
        newEmployee.setRegistrationDate(employee.getRegisteredAt());
        return newEmployee;
    }

    public static List<EmployeeHttpResponse> mapToListHttpResponse(Iterable<Employee> employees){

        List<EmployeeHttpResponse> employeeHttpResponseList = new ArrayList<>();

        employees.forEach((employee -> {

            EmployeeHttpResponse employeeHttpResponse = new EmployeeHttpResponse();
            employeeHttpResponse.setDepartmentHttpResponse(generateDepartmentHttpResponse(employee.getDepartment()));
            employeeHttpResponse.setId(employee.getId());
            employeeHttpResponse.setAge(employee.getAge());
            employeeHttpResponse.setRegistrationDate(employee.getRegisteredAt());
            employeeHttpResponse.setFirstName(employee.getFirstName());
            employeeHttpResponse.setLastName(employee.getLastName());

            employeeHttpResponseList.add(employeeHttpResponse);
        }));

        return employeeHttpResponseList;
    }

    private static DepartmentHttpResponse generateDepartmentHttpResponse(Department department){
        DepartmentHttpResponse departmentHttpResponse = new DepartmentHttpResponse();

        departmentHttpResponse.setId(department.getId());
        departmentHttpResponse.setName(department.getName());

        return departmentHttpResponse;
    }

}
