package com.disid.api.employees.services;

import com.disid.api.departments.models.Department;
import com.disid.api.departments.repositories.DepartmentsRepository;
import com.disid.api.employees.controllers.httpRequests.EmployeeHttpRequest;
import com.disid.api.employees.controllers.httpResponses.EmployeeHttpResponse;
import com.disid.api.employees.models.Employee;
import com.disid.api.employees.repositories.EmployeesRepository;
import com.disid.api.employees.utils.EmployeesMappers;
import com.disid.api.employees.utils.EmployeesValidators;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {

    private EmployeesRepository employeesRepository;

    private DepartmentsRepository departmentsRepository;

    public EmployeesService(EmployeesRepository employeesRepository, DepartmentsRepository departmentsRepository) {
        this.employeesRepository = employeesRepository;
        this.departmentsRepository = departmentsRepository;
    }

    public EmployeeHttpResponse createEmployee(EmployeeHttpRequest request) {

        EmployeesValidators.validateEmployeeHttpRequest(request);

        Optional<Department> departmentOptional
                = departmentsRepository.findById(request.getDepartmentId());


        Employee newEmployee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                EmployeesValidators.validateDepartment(departmentOptional),
                request.getAge(),
                request.getRegisteredAt());

        return EmployeesMappers.mapToHttpResponse(employeesRepository.save(newEmployee));

    }

    public Page<EmployeeHttpResponse> getEmployees(Predicate predicate, Pageable pageable) {

        Iterable<Employee> pageResponse = employeesRepository.findAll(
                predicate, pageable);

        List<EmployeeHttpResponse> employeeHttpResponseList = EmployeesMappers.mapToListHttpResponse(pageResponse);

        return new PageImpl<EmployeeHttpResponse>(employeeHttpResponseList,pageable,employeeHttpResponseList.size());
    }

    public void deleteEmployee(long id) {

        Optional<Employee> employeeOptional
                = employeesRepository.findById(id);

        if(employeeOptional.isPresent()){
            employeesRepository.delete(employeeOptional.get());
        }else{
            throw new IllegalArgumentException("Employee to be deleted with Id "+id+ " could not be found, please change the id");
        }

    }

    public EmployeeHttpResponse updateEmployee(Long employeeId, EmployeeHttpRequest request) {
        EmployeesValidators.validateEmployeeHttpRequest(request);

        Optional<Employee> employeeOptional = employeesRepository.findById(employeeId);
        Optional<Department> departmentOptional
                = departmentsRepository.findById(request.getDepartmentId());
        Department department = EmployeesValidators.validateDepartment(departmentOptional);

        if(employeeOptional.isPresent()){
            Employee employeeToBeUpdated = employeeOptional.get();
            employeeToBeUpdated.setDepartment(department);
            employeeToBeUpdated.setFirstName(request.getFirstName());
            employeeToBeUpdated.setLastName(request.getLastName());
            employeeToBeUpdated.setAge(request.getAge());
            employeeToBeUpdated.setRegisteredAt(request.getRegisteredAt() != null ? request.getRegisteredAt() : employeeToBeUpdated.getRegisteredAt());

            return EmployeesMappers.mapToHttpResponse(employeesRepository.save(employeeToBeUpdated));
        }else{
            throw new IllegalArgumentException("Employee to be updated with Id "+employeeId+ " could not be found, please change the id");
        }
    }

    public Page<EmployeeHttpResponse> getEmployeesBySeniorityDate(String seniorityDate, Pageable pageable) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Iterable<Employee> pageResponse = employeesRepository.getEmployeeByRegisteredAtGreaterThanEqual(LocalDate.parse(seniorityDate, formatter));

        List<EmployeeHttpResponse> employeeHttpResponseList = EmployeesMappers.mapToListHttpResponse(pageResponse);

        return new PageImpl<EmployeeHttpResponse>(employeeHttpResponseList,pageable,employeeHttpResponseList.size());
    }
}
