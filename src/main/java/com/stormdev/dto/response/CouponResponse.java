/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.dto.response;

import com.stormdev.domain.model.CouponStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        Instant expirationDate,
        CouponStatus status,
        boolean published,
        boolean redeemed
) {
}
