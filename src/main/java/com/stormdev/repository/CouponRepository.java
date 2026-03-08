/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stormdev.domain.model.Coupon;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    Optional<Coupon> findByCode(String code);

    boolean existsByCodeAndDeletedFalse(String code);
}

