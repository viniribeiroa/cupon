/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.domain.model;

import com.stormdev.domain.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE coupons SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Coupon {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 6)
    private String code;

    @Column(name = "discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    private Coupon(String code, BigDecimal discount, LocalDate expirationDate) {
        this.code = sanitizeCode(code);
        this.discount = validateDiscount(discount);
        this.expirationDate = validateExpirationDate(expirationDate);
        this.deleted = false;
    }

    public static Coupon create(String code, BigDecimal discount, LocalDate expirationDate) {
        return new Coupon(code, discount, expirationDate);
    }

    public void markAsDeleted() {
        if (this.deleted) {
            throw new BusinessException("Coupon already deleted");
        }
        this.deleted = true;
    }

    private static String sanitizeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new BusinessException("Coupon code is required");
        }

        String sanitized = code.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        if (sanitized.length() != 6) {
            throw new BusinessException("Coupon code must contain exactly 6 alphanumeric characters");
        }

        return sanitized;
    }

    private static BigDecimal validateDiscount(BigDecimal discount) {
        if (discount == null) {
            throw new BusinessException("Discount is required");
        }

        if (discount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Discount must be greater than zero");
        }

        return discount;
    }

    private static LocalDate validateExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new BusinessException("Expiration date is required");
        }

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new BusinessException("Expiration date cannot be in the past");
        }

        return expirationDate;
    }
}
