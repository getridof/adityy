package com.healthcare.enrolmentservice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EnrollmentServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createEnrollmentShouldReturnSuccess() throws Exception {
        JSONObject jsonObject =  new JSONObject("{\n" +
                "    \"name\":\"adithya\",\n" +
                "    \"activationStatus\":true,\n" +
                "    \"dateOfBirth\":\"2020-01-01\"\n" +
                "    \n" +
                "}");

        this.mockMvc.perform(post("/enrollees", jsonObject)).andExpect(status().isCreated());
    }
}
