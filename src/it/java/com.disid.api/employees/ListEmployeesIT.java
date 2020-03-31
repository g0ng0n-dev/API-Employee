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

public class ListEmployeesIT extends BaseApiApplication {


    @Autowired
    private MockMvc mvc;


    /**
     * 3 - Listar varios empleados de un departamento
     * <p>
     * GET - /api/v1/employees?departmentId={departmentId}
     * <p>
     *
     * <p>
     * Status Code: 200
     * Json Response
     *{
     *     "content": [
     *         {
     *             "id": 2,
     *             "firstName": "Gonzalo",
     *             "lastName": "Gisbert",
     *             "department": {
     *                 "id": 1,
     *                 "name": "HR"
     *             },
     *             "registrationDate": "2019-09-19",
     *             "age": 32
     *         }
     *     ],
     *     "pageable": {
     *         "sort": {
     *             "sorted": true,
     *             "unsorted": false,
     *             "empty": false
     *         },
     *         "offset": 0,
     *         "pageNumber": 0,
     *         "pageSize": 500,
     *         "paged": true,
     *         "unpaged": false
     *     },
     *     "totalPages": 1,
     *     "last": true,
     *     "totalElements": 1,
     *     "size": 10,
     *     "number": 0,
     *     "sort": {
     *         "sorted": true,
     *         "unsorted": false,
     *         "empty": false
     *     },
     *     "first": true,
     *     "numberOfElements": 1,
     *     "empty": false
     * }
     * <p>
     */
    @Test
    public void getEmployeesFilteredByDepartment() throws Exception {
        CreateDepartmentHttpRequest hrRequestToBeCreated = new CreateDepartmentHttpRequest();
        hrRequestToBeCreated.setName("HR");


        MvcResult depResponse = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(hrRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andReturn();
        DepartmentHttpResponse hrCreated = new ObjectMapper().readValue(depResponse.getResponse().getContentAsString(), DepartmentHttpResponse.class);

        CreateDepartmentHttpRequest financeRequestToBeCreated = new CreateDepartmentHttpRequest();
        financeRequestToBeCreated.setName("Finance");


        MvcResult depFinance = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(financeRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Finance"))
                .andReturn();

        DepartmentHttpResponse financeCreated = new ObjectMapper().readValue(depFinance.getResponse().getContentAsString(), DepartmentHttpResponse.class);

        String employee1 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String employee2 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String templateEmployee3 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": %s,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String employee3 = String.format(templateEmployee3, financeCreated.getId());


        createEmployee(employee1);

        createEmployee(employee2);

        createEmployee(employee3);

        String template = "/api/v1/employees?departmentId=%s";
        String employeeFilteredByDepartmentURL = String.format(template, hrCreated.getId());

        mvc.perform(
                MockMvcRequestBuilders.get(employeeFilteredByDepartmentURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].department.id")
                        .value(hrCreated.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].department.id")
                        .value(hrCreated.getId()));

    }

    /**
     * 4 - Listar un empleado concreto que pertenece a un departamento
     * <p>
     * GET - /api/v1/employees?firstName=Gonzalo&lastName=Gisbert&departmentId=1
     * <p>
     *
     * <p>
     * Status Code: 200
     * Json Response
     *{
     *     "content": [
     *         {
     *             "id": 2,
     *             "firstName": "Gonzalo",
     *             "lastName": "Gisbert",
     *             "department": {
     *                 "id": 1,
     *                 "name": "HR"
     *             },
     *             "registrationDate": "2019-09-19",
     *             "age": 32
     *         }
     *     ],
     *     "pageable": {
     *         "sort": {
     *             "sorted": true,
     *             "unsorted": false,
     *             "empty": false
     *         },
     *         "offset": 0,
     *         "pageNumber": 0,
     *         "pageSize": 500,
     *         "paged": true,
     *         "unpaged": false
     *     },
     *     "totalPages": 1,
     *     "last": true,
     *     "totalElements": 1,
     *     "size": 10,
     *     "number": 0,
     *     "sort": {
     *         "sorted": true,
     *         "unsorted": false,
     *         "empty": false
     *     },
     *     "first": true,
     *     "numberOfElements": 1,
     *     "empty": false
     * }
     * <p>
     */
    @Test
    public void getAnEmployeeFilteredByNameAndByDepartment() throws Exception {
        CreateDepartmentHttpRequest hrRequestToBeCreated = new CreateDepartmentHttpRequest();
        hrRequestToBeCreated.setName("HR");


        MvcResult depResponse = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(hrRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andReturn();
        DepartmentHttpResponse hrCreated = new ObjectMapper().readValue(depResponse.getResponse().getContentAsString(), DepartmentHttpResponse.class);

        CreateDepartmentHttpRequest financeRequestToBeCreated = new CreateDepartmentHttpRequest();
        financeRequestToBeCreated.setName("Finance");


        MvcResult depFinance = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(asJsonString(financeRequestToBeCreated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Finance"))
                .andReturn();

        DepartmentHttpResponse financeCreated = new ObjectMapper().readValue(depFinance.getResponse().getContentAsString(), DepartmentHttpResponse.class);

        String employeeToBeObtained = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Gonzalo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String employee2 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Alexander\",\n" +
                "  \"lastName\": \"Cannigia\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String employee5 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Leo\",\n" +
                "  \"lastName\": \"Gisbert\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String templateEmployee3 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": %s,\n" +
                "  \"firstName\": \"Roberto\",\n" +
                "  \"lastName\": \"Soldado\",\n" +
                "  \"registeredAt\": \"2016-09-09\"\n" +
                "}";

        String employee3 = String.format(templateEmployee3, financeCreated.getId());


        createEmployee(employeeToBeObtained);

        createEmployee(employee2);

        createEmployee(employee5);

        createEmployee(employee3);

        String template = "/api/v1/employees?firstName=Gonzalo&lastName=Gisbert&departmentId=%s";
        String employeeFilteredByNameAndDepartmentURL = String.format(template, hrCreated.getId());

        mvc.perform(
                MockMvcRequestBuilders.get(employeeFilteredByNameAndDepartmentURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName")
                        .value("Gonzalo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName")
                        .value("Gisbert"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].department.id")
                        .value(hrCreated.getId()));

    }



    private void createEmployee(String employee) throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/employees")
                        .content(employee)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
