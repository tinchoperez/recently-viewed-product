package com.exercise.recentlyviewedprod.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustProdHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShouldReturnMessage() throws Exception {
        mockMvc.perform(post("/api/v1/rvp/saveProduct/1/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetViewedProductsByCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/rvp/viewedProducts/1/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteViewedProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/rvp/deleteProduct/1/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateViewedProduct() throws Exception {
        mockMvc.perform(put("/api/v1/rvp/updateProduct/1/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


}
