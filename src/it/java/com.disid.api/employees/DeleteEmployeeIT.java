package com.disid.api.employees;

import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.shared.BaseApiApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteEmployeeIT extends BaseApiApplication {

    @Autowired
    private MockMvc mvc;

    /**
     * 5 - Borrar Empleado
     *
     * <p>
     * DELETE - /api/v1/employees/{employeeId}
     * <p>
     *
     * <p>
     * Status Code: 200
     */
    @Test
    public void deleteEmployeeById() throws Exception {

        CreateDepartmentHttpRequest hrRequestToBeCreated = new CreateDepartmentHttpRequest();
        hrRequestToBeCreated.setName("HR");


        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(hrRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andReturn();

        String employeeToBeDelete = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeToBeDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id")
                                .value("2"));

        String template = "/api/v1/employees/%s";
        String deleteEmployeeURL = String.format(template, "2");

        mvc.perform(
                MockMvcRequestBuilders.delete(deleteEmployeeURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());

    }
}
