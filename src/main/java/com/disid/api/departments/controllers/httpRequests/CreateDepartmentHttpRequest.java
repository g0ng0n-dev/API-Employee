package com.disid.api.departments.controllers.httpRequests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing the Http Request body that we use to create a department.")
public class CreateDepartmentHttpRequest {

    @ApiModelProperty(notes = "Name of the department.", example = "Finance", required = true, position = 1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}