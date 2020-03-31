package com.disid.api.departments;

import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.departments.models.Department;
import com.disid.api.departments.repositories.DepartmentsRepository;
import com.disid.api.departments.services.DepartmentsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentsServiceTest {

    DepartmentsRepository departmentsRepository;

    private DepartmentsService service;

    @Before
    public void setUp() {
        departmentsRepository = mock(DepartmentsRepository.class);
        service = new DepartmentsService(departmentsRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDepartmentReturnsErrorBecauseOfDepartmentDuplication(){
        String newDept = "Finance";
        CreateDepartmentHttpRequest newHttpRequest = new CreateDepartmentHttpRequest();
        newHttpRequest.setName(newDept);
        when(departmentsRepository.existsByName(newDept)).thenReturn(true);
        service.createDepartment(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDepartmentReturnsErrorNameIsBlank(){
        String newDept = "";
        CreateDepartmentHttpRequest newHttpRequest = new CreateDepartmentHttpRequest();
        newHttpRequest.setName(newDept);
        when(departmentsRepository.existsByName(newDept)).thenReturn(true);
        service.createDepartment(newHttpRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDepartmentReturnsErrorNameIsNull(){
        CreateDepartmentHttpRequest newHttpRequest = new CreateDepartmentHttpRequest();
        newHttpRequest.setName(null);
        service.createDepartment(newHttpRequest);
    }

    @Test
    public void createDepartmentReturnsNewDepartment(){
        String newDeptName = "Finance";
        CreateDepartmentHttpRequest newHttpRequest = new CreateDepartmentHttpRequest();
        newHttpRequest.setName(newDeptName);

        Department departmentSaved = new Department();
        departmentSaved.setName(newDeptName);
        departmentSaved.setId(1L);

        when(departmentsRepository.existsByName(newDeptName)).thenReturn(false);
        when(departmentsRepository.save(any(Department.class))).thenReturn(departmentSaved);

        DepartmentHttpResponse newDeptResponse =
                service.createDepartment(newHttpRequest);

        Assert.assertNotNull(newDeptResponse);
        Assert.assertEquals(newDeptName, newDeptResponse.getName());

    }

}
