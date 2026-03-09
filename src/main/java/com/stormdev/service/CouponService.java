/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.service;

import com.stormdev.domain.exception.BusinessException;
import com.stormdev.domain.exception.ResourceNotFoundException;
import com.stormdev.domain.model.Coupon;
import com.stormdev.dto.request.CouponCreateRequest;
import com.stormdev.dto.response.CouponResponse;
import com.stormdev.mapper.CouponMapper;
import com.stormdev.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;
    private final CouponMapper mapper;

    @Transactional
    public CouponResponse create(CouponCreateRequest request) {
        Coupon coupon = Coupon.create(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        );

        if (repository.existsByCodeAndDeletedFalse(coupon.getCode())) {
            throw new BusinessException("Coupon code already exists");
        }

        Coupon saved = repository.save(coupon);
        saved.refreshStatus();

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(UUID id) {
        Coupon coupon = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        coupon.refreshStatus();
        return mapper.toResponse(coupon);
    }

    @Transactional
    public void delete(UUID id) {
        Coupon coupon = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        coupon.delete();
        repository.save(coupon);
    }
}