package com.disid.api.employees.controllers.httpResponses;

import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class EmployeeHttpResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("department")
    private DepartmentHttpResponse departmentHttpResponse;

    @JsonProperty("registrationDate")
    private LocalDate registrationDate;

    @JsonProperty("age")
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DepartmentHttpResponse getDepartmentHttpResponse() {
        return departmentHttpResponse;
    }

    public void setDepartmentHttpResponse(DepartmentHttpResponse departmentHttpResponse) {
        this.departmentHttpResponse = departmentHttpResponse;
    }
}
