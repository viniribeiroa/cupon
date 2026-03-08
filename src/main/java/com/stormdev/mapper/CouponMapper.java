/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.mapper;

import org.mapstruct.Mapper;

import com.stormdev.domain.model.Coupon;
import com.stormdev.dto.response.CouponResponse;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    CouponResponse toResponse(Coupon coupon);
}

