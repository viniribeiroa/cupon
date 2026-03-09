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
import java.time.Instant;

public record CouponCreateRequest(

        @NotBlank(message = "code is required")
        String code,

        @NotBlank(message = "description is required")
        String description,

        @NotNull(message = "discountValue is required")
        @DecimalMin(value = "0.01", message = "discountValue must be greater than zero")
        BigDecimal discountValue,

        @NotNull(message = "expirationDate is required")
        Instant expirationDate,

        @NotNull(message = "published is required")
        Boolean published
) {
}

