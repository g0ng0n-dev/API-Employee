package com.disid.api.employees;

import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.departments.controllers.httpResponses.DepartmentHttpResponse;
import com.disid.api.shared.BaseApiApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateEmployeeIT extends BaseApiApplication {


    @Autowired
    private MockMvc mvc;


    /**
     * 1 - Crear un Empleado
     * <p>
     * POST - /api/v1/employees
     * <p>
     * json body example: {
     * "age": 32,
     * "departmentId": 1,
     * "firstName": "Gonzalo",
     * "lastName": "Gisbert",
     * "registeredAt": "2016-09-09"
     * }
     * <p>
     * Status Code: 200
     * Json Response {
     * "id": 2,
     * "firstName": "Gonzalo",
     * "lastName": "Gisbert",
     * "department": {
     * "id": 1,
     * "name": "HR"
     * },
     * "registrationDate": "2016-09-09",
     * "age": 32
     * }
     */
    @Test
    public void createEmployee() throws Exception {

        CreateDepartmentHttpRequest createDepartmentHttpRequest = new CreateDepartmentHttpRequest();
        createDepartmentHttpRequest.setName("HR");


        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(createDepartmentHttpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"));

        String employeeHttpRequest = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeHttpRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(32))
                // .andExpect(MockMvcResultMatchers.jsonPath("$.registrationDate").value("2016-09-09"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gonzalo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gisbert"));
    }

    /**
     * 1 - Crear un Empleado; Validacion del campo firstName
     * <p>
     * POST - /api/v1/employees
     * <p>
     * Json Request body:
     * {
     * "age": 32,
     * "departmentId": 1,
     * "name": "Gonzalo",
     * }
     * <p>
     * Status Code: 400
     * Json Response
     * {
     * "status": "BAD_REQUEST",
     * "message": "The First Name field is mandatory"
     * }
     */
    @Test
    public void createEmployeeBadRequestFirstNameValidation() throws Exception {

        CreateDepartmentHttpRequest createDepartmentHttpRequest = new CreateDepartmentHttpRequest();
        createDepartmentHttpRequest.setName("HR");


        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(createDepartmentHttpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"));

        String employeeHttpRequest = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"name\": \"444444\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeHttpRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The First Name field is mandatory"));
    }

    /**
     * 1 - Crear un Empleado; Validacion del campo last Name
     * <p>
     * POST - /api/v1/employees
     * <p>
     * Json Request body:
     * {
     * "age": 32,
     * "departmentId": 1,
     * "firstName": "Gonzalo"
     * }
     * <p>
     * Status Code: 400
     * Json Response
     * {
     * "status": "BAD_REQUEST",
     * "message": "The Last Name field is mandatory"
     * }
     */
    @Test
    public void createEmployeeBadRequest() throws Exception {

        CreateDepartmentHttpRequest createDepartmentHttpRequest = new CreateDepartmentHttpRequest();
        createDepartmentHttpRequest.setName("HR");


        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(createDepartmentHttpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"));

        String employeeHttpRequest = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeHttpRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The Last Name field is mandatory"));
    }
}