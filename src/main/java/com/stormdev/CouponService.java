/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
