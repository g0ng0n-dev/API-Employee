package com.disid.api.departments;


import com.disid.api.departments.controllers.httpRequests.CreateDepartmentHttpRequest;
import com.disid.api.shared.BaseApiApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDepartmentsIT extends BaseApiApplication {

    @Autowired
    private MockMvc mvc;

    /**
     * 2 - Crear un departamento
     * <p>
     * POST - /api/v1/departments
     * <p>
     * Json Request body:
     * {
     * "name": "HR"
     * }
     * <p>
     * Status Code: 200
     * Json Response
     *{
     *     "id": 1,
     *     "name": "HR2222"
     * }
     */
    @Test
    public void createDepartment() throws Exception {

        CreateDepartmentHttpRequest createDepartmentHttpRequest = new CreateDepartmentHttpRequest();
        createDepartmentHttpRequest.setName("HR");

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/departments")
                .content(asJsonString(createDepartmentHttpRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));

    }
}
