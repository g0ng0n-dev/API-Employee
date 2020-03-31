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

public class ModifyEmployeeIT  extends BaseApiApplication {



    @Autowired
    private MockMvc mvc;

    /**
     * 6 - Modificar los atributos de la entidad Empleado
     */
    @Test
    public void modifyEmployeeById() throws Exception {

        CreateDepartmentHttpRequest hrRequestToBeCreated = new CreateDepartmentHttpRequest();
        hrRequestToBeCreated.setName("HR");

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(hrRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"));

        CreateDepartmentHttpRequest financeRequestToBeCreated = new CreateDepartmentHttpRequest();
        financeRequestToBeCreated.setName("Finance");

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(financeRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Finance"));

        String employeeToBeCreated = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeToBeCreated)
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
                        .value("3"));

        String employeeToBeModified = "{\n" +
                "  \"age\": 33,\n" +
                "  \"departmentId\": 2,\n" +
                "  \"firstName\": \"Alejandro\",\n" +
                "  \"lastName\": \"Gomez\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String template = "/api/v1/employees/%s";
        String updateEmployeeUrl = String.format(template, "3");

        mvc.perform(
                MockMvcRequestBuilders.put(updateEmployeeUrl)
                        .content(employeeToBeModified)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                        .value("Alejandro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                        .value("Gomez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department.id")
                        .value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age")
                        .value("33"));

    }


    /**
     * 6 - Modificar los atributos de la entidad Empleado - Validacion campo firstName
     */
    @Test
    public void modifyEmployeeByIdFailedBecauseOfBadRequest() throws Exception {

        CreateDepartmentHttpRequest hrRequestToBeCreated = new CreateDepartmentHttpRequest();
        hrRequestToBeCreated.setName("HR");


        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(hrRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"));

        CreateDepartmentHttpRequest financeRequestToBeCreated = new CreateDepartmentHttpRequest();
        financeRequestToBeCreated.setName("Finance");

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(financeRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Finance"));

        String employeeToBeCreated = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employeeToBeCreated)
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
                        .value("3"));

        String employeeToBeModified = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"name\": \"Gonzalo\"\n" +
                "}";

        String template = "/api/v1/employees/%s";
        String updateEmployeeUrl = String.format(template, "3");

        mvc.perform(
                MockMvcRequestBuilders.put(updateEmployeeUrl)
                        .content(employeeToBeModified)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The First Name field is mandatory"));

    }
}
