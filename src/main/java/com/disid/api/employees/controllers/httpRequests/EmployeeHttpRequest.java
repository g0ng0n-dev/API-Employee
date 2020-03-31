package com.disid.api.employees.controllers.httpRequests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@ApiModel(description = "Class representing the Http Request body that we use to create an employee.")
public class EmployeeHttpRequest {

    @ApiModelProperty(notes = "First Name of the Employee.", example = "Gonzalo", required = true, position = 1)
    private String firstName;

    @ApiModelProperty(notes = "Last Name of the Employee.", example = "Gis", required = true, position = 2)
    private String lastName;

    @ApiModelProperty(notes = "The Id of the department that the employee to which it will belong.", example = "1", required = true, position = 3)
    private Long departmentId;

    @ApiModelProperty(notes = "Age of the Employee.", example = "32", required = true, position = 4)
    private Integer age;

    @ApiModelProperty(notes = "Date of the employee that was registered", example ="2016-11-09", required= true, position = 5)
    private LocalDate registeredAt;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }
}
