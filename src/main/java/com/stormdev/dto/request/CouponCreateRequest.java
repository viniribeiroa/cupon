/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponCreateRequest(
        @NotBlank(message = "Code is required")
        String code,

        @NotNull(message = "Discount is required")
        @DecimalMin(value = "0.01", message = "Discount must be greater than zero")
        BigDecimal discount,

        @NotNull(message = "Expiration date is required")
        LocalDate expirationDate
) {
}

