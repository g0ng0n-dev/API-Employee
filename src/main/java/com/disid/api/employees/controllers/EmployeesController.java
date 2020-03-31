package com.disid.api.employees.controllers;

import com.disid.api.employees.controllers.httpRequests.EmployeeHttpRequest;
import com.disid.api.employees.controllers.httpResponses.EmployeeHttpResponse;
import com.disid.api.employees.models.Employee;
import com.disid.api.employees.services.EmployeesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import com.querydsl.core.types.Predicate;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/")
public class EmployeesController {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private EmployeesService employeesService;

    @RequestMapping(path = "employees", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("Creates a new Employee.")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeHttpResponse createEmployee(@ApiParam("Employee information for a new Employee to be created.")
                                                   @Valid @RequestBody EmployeeHttpRequest request) {
        logger.debug("Creating an Employee");
        return employeesService.createEmployee(request);
    }

    @RequestMapping(path = "employees", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("Get a List of Employees Filtered or not")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeHttpResponse> getEmployees(@QuerydslPredicate(root = Employee.class) Predicate predicate,
                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "500") int size)
                                                         {
        logger.debug("Getting a list of Employees");

        return employeesService.getEmployees(predicate, createPageRequest(page,size));
    }

    @RequestMapping(value = "employees/{id}", method = RequestMethod.DELETE)
    @ApiOperation("Removing an Employee from the System")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {

        logger.debug("Removing an Employee");
        employeesService.deleteEmployee(id);
    }

    @RequestMapping(path = "employees/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation("Updates an Employee.")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeHttpResponse updateEmployee(@ApiParam("Employee Id") @PathVariable("id") long id,
                                                 @ApiParam("Employee information for the Employee to be updated.")
                                                 @Valid @RequestBody EmployeeHttpRequest request) {
        logger.debug("updating an Employee");
        return employeesService.updateEmployee(id, request);
    }

    @RequestMapping(path = "employees/seniority", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("Gets a List of employees that has a registered at date greater than the seniority date received.")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeHttpResponse> getEmployeeBySeniority(
            @ApiParam("Employee Id") @RequestParam String seniorityDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size){

        logger.debug("Getting a List of Employees where seniority date is greater than registered date");
        return employeesService.getEmployeesBySeniorityDate(seniorityDate, createPageRequest(page,size));
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.Direction.ASC, "id");

    }
}
