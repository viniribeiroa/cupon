/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stormdev.domain.exception.BusinessException;
import com.stormdev.domain.exception.ResourceNotFoundException;
import com.stormdev.domain.model.Coupon;
import com.stormdev.dto.request.CouponCreateRequest;
import com.stormdev.dto.response.CouponResponse;
import com.stormdev.mapper.CouponMapper;
import com.stormdev.repository.CouponRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Transactional
    public CouponResponse create(CouponCreateRequest request) {
        Coupon coupon = Coupon.create(request.code(), request.discount(), request.expirationDate());

        if (couponRepository.existsByCodeAndDeletedFalse(coupon.getCode())) {
            throw new BusinessException("Coupon code already exists");
        }

        Coupon saved = couponRepository.save(coupon);
        return couponMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        return couponMapper.toResponse(coupon);
    }

    @Transactional
    public void delete(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        coupon.markAsDeleted();
        couponRepository.save(coupon);
    }
}
