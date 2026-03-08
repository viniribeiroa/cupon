/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        String code,
        BigDecimal discount,
        LocalDate expirationDate
) {
}
