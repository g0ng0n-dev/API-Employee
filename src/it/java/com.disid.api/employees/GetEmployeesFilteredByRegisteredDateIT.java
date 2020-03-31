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

public class GetEmployeesFilteredByRegisteredDateIT extends BaseApiApplication {

    @Autowired
    private MockMvc mvc;


    /**
     * 7 - Buscar un empleado por antigüedad en la empresa, mostrando todos los que se han incorporado a la empresa con posterioridad a una fecha
     * <p>
     * GET - /api/v1/employees/seniority?seniorityDate=2016-09-09
     * <p>
     *
     * <p>
     * Status Code: 200
     * Json Response
     *{
     *     "content": [
     *         {
     *             "id": 2,
     *             "firstName": "Gonzalo3",
     *             "lastName": "Gisbert3",
     *             "department": {
     *                 "id": 1,
     *                 "name": "HR2222"
     *             },
     *             "registrationDate": "2016-09-11",
     *             "age": 32
     *         },
     *         {
     *             "id": 3,
     *             "firstName": "Gonzalo2",
     *             "lastName": "Gisbert2",
     *             "department": {
     *                 "id": 1,
     *                 "name": "HR2222"
     *             },
     *             "registrationDate": "2016-09-10",
     *             "age": 32
     *         },
     *         {
     *             "id": 4,
     *             "firstName": "Gonzalo",
     *             "lastName": "Gisbert",
     *             "department": {
     *                 "id": 1,
     *                 "name": "HR2222"
     *             },
     *             "registrationDate": "2016-09-09",
     *             "age": 32
     *         }
     *     ],
     *     "pageable": {
     *         "sort": {
     *             "unsorted": false,
     *             "sorted": true,
     *             "empty": false
     *         },
     *         "offset": 0,
     *         "pageSize": 10,
     *         "pageNumber": 0,
     *         "paged": true,
     *         "unpaged": false
     *     },
     *     "totalElements": 3,
     *     "totalPages": 1,
     *     "last": true,
     *     "size": 10,
     *     "number": 0,
     *     "numberOfElements": 3,
     *     "first": true,
     *     "sort": {
     *         "unsorted": false,
     *         "sorted": true,
     *         "empty": false
     *     },
     *     "empty": false
     * }
     * <p>
     */
    @Test
    public void getEmployeesRegisteredAfterTheDateGiven() throws Exception {
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

        String employeeOut = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": 1,\n" +
                "  \"firstName\": \"Batman\",\n" +
                "  \"lastName\": \"Robin\",\n" +
                "  \"registeredAt\": \"2016-09-08\"\n" +
                "}";

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
                "  \"firstName\": \"Gonzalo2\",\n" +
                "  \"lastName\": \"Gisbert2\",\n" +
                "  \"registeredAt\": \"2016-09-10\"\n" +
                "}";

        String templateEmployee3 = "{\n" +
                "  \"age\": 32,\n" +
                "  \"departmentId\": %s,\n" +
                "  \"firstName\": \"Gonzalo3\",\n" +
                "  \"lastName\": \"Gisbert3\",\n" +
                "  \"registeredAt\": \"2016-09-11\"\n" +
                "}";

        String employee3 = String.format(templateEmployee3, financeCreated.getId());

        createEmployee(employeeOut);

        createEmployee(employee1);

        createEmployee(employee2);

        createEmployee(employee3);

        String template = "/api/v1/employees/seniority?seniorityDate=2016-09-09";
        String employeeFilteredByDepartmentURL = String.format(template, hrCreated.getId());

        mvc.perform(
                MockMvcRequestBuilders.get(employeeFilteredByDepartmentURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName")
                        .value("Gonzalo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName")
                        .value("Gisbert"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].registrationDate")
                        .value("2016-09-09"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].firstName")
                        .value("Gonzalo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].lastName")
                        .value("Gisbert2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].registrationDate")
                        .value("2016-09-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].firstName")
                        .value("Gonzalo3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].lastName")
                        .value("Gisbert3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].registrationDate")
                        .value("2016-09-11"));
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
