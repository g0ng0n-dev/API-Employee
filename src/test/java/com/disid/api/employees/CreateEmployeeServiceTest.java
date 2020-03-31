package com.disid.api.employees;

import com.disid.api.departments.models.Department;
import com.disid.api.departments.repositories.DepartmentsRepository;
import com.disid.api.employees.controllers.httpRequests.EmployeeHttpRequest;
import com.disid.api.employees.controllers.httpResponses.EmployeeHttpResponse;
import com.disid.api.employees.models.Employee;
import com.disid.api.employees.repositories.EmployeesRepository;
import com.disid.api.employees.services.EmployeesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateEmployeeServiceTest {

    DepartmentsRepository departmentsRepository;

    EmployeesRepository employeesRepository;

    private EmployeesService service;


    @Before
    public void setUp() {
        departmentsRepository = mock(DepartmentsRepository.class);
        employeesRepository = mock(EmployeesRepository.class);
        service = new EmployeesService(employeesRepository, departmentsRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmployeeReturnsErrorBecauseDepartmentNotFound(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("Gon");
        newHttpRequest.setLastName("Gis");
        newHttpRequest.setAge(1);
        newHttpRequest.setDepartmentId(111L);

        when(departmentsRepository.findById(anyLong())).thenReturn(Optional.empty());

        service.createEmployee(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmployeeReturnsErrorBecauseOfBadRequestFirstNameIsBlank(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("");

        service.createEmployee(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmployeeReturnsErrorBecauseOfBadRequestLastNameIsBlank(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("Gon");
        newHttpRequest.setLastName("");

        service.createEmployee(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmployeeReturnsErrorBecauseOfBadRequestAgeIsNull(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("");
        newHttpRequest.setLastName("Gis");
        newHttpRequest.setDepartmentId(111L);

        service.createEmployee(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmployeeReturnsErrorBecauseOfBadRequestDepartmentIdIsNull(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("");
        newHttpRequest.setLastName("Gis");
        newHttpRequest.setAge(1);

        service.createEmployee(newHttpRequest);
    }

    @Test
    public void createEmployeeReturnsNewEmployee(){
        EmployeeHttpRequest newHttpRequest = new EmployeeHttpRequest();
        newHttpRequest.setFirstName("Gon");
        newHttpRequest.setLastName("Gis");
        newHttpRequest.setAge(32);
        newHttpRequest.setDepartmentId(111L);
        newHttpRequest.setRegisteredAt(LocalDate.now());

        Department department = new Department();
        department.setId(111L);
        department.setName("Finance");

        when(departmentsRepository.findById(111L)).thenReturn(Optional.of(department));

        Employee newEmployee = new Employee();
        newEmployee.setId(1L);
        newEmployee.setAge(32);
        newEmployee.setDepartment(department);
        newEmployee.setFirstName("Gon");
        newEmployee.setLastName("Gis");
        newEmployee.setRegisteredAt(LocalDate.now());
        when(employeesRepository.save(any(Employee.class))).thenReturn(newEmployee);

        EmployeeHttpResponse employeeHttpResponse =
                service.createEmployee(newHttpRequest);

        Assert.assertNotNull(employeeHttpResponse);
        Assert.assertEquals(1L, employeeHttpResponse.getId());

    }

}
