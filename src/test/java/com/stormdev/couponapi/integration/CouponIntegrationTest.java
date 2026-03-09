/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.couponapi.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormdev.dto.request.CouponCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;



import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class CouponIntegrationTest {

  
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCouponSuccessfully() throws Exception {
        CouponCreateRequest request = new CouponCreateRequest(
                "AB-12CD",
                "Cupom de lançamento",
                new BigDecimal("20.00"),
                Instant.now().plus(15, ChronoUnit.DAYS),
                true
        );

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.code").value("AB12CD"))
                .andExpect(jsonPath("$.description").value("Cupom de lançamento"))
                .andExpect(jsonPath("$.discountValue").value(20.00))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.published").value(true))
                .andExpect(jsonPath("$.redeemed").value(false))
                .andExpect(jsonPath("$.expirationDate").isNotEmpty());
    }

    @Test
    void shouldReturnValidationErrorWhenRequestIsInvalid() throws Exception {
        String invalidPayload = """
                {
                  "code": "",
                  "description": "",
                  "discountValue": 0,
                  "expirationDate": null,
                  "published": null
                }
                """;

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.messages").isArray());
    }

    @Test
    void shouldSoftDeleteCouponAndHideItFromSubsequentReads() throws Exception {
        CouponCreateRequest request = new CouponCreateRequest(
                "ZX-98YU",
                "Cupom para teste de exclusão",
                new BigDecimal("30.00"),
                Instant.now().plus(20, ChronoUnit.DAYS),
                false
        );

        String responseBody = mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("id").asText();

        mockMvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(header().doesNotExist("Content-Type"));

        mockMvc.perform(get("/coupon/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"))
                .andExpect(jsonPath("$.messages[0]").value("Coupon not found"));
    }
}