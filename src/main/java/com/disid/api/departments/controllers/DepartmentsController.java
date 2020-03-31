package com.disid.api.departments.controllers;

import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.departments.services.DepartmentsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class DepartmentsController {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DepartmentsService departmentsService;

    @RequestMapping(path = "departments", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("Creates a new department.")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentHttpResponse createDepartment(@ApiParam("Department information for a new department to be created.")
                                     @Valid @RequestBody CreateDepartmentHttpRequest request) {
        logger.debug("Creating a Department");
        return departmentsService.createDepartment(request);
    }
}
