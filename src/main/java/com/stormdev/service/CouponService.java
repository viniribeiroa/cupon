/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.service;

import com.stormdev.common.constants.ErrorMessages;
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
            throw new BusinessException(ErrorMessages.COUPON_CODE_ALREADY_EXISTS);
        }

        Coupon saved = repository.save(coupon);
        saved.refreshStatus();

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(UUID id) {
        Coupon coupon = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COUPON_NOT_FOUND));

        coupon.refreshStatus();
        return mapper.toResponse(coupon);
    }

    @Transactional
    public void delete(UUID id) {
        Coupon coupon = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COUPON_NOT_FOUND));

        coupon.delete();
        repository.save(coupon);
    }
}