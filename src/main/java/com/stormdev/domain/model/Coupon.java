/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.domain.model;

import com.stormdev.domain.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE coupons SET deleted = true WHERE id = ?")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponStatus status;

    @Column(nullable = false)
    private boolean published;

    @Column(nullable = false)
    private boolean redeemed;

    @Column(nullable = false)
    private boolean deleted;

    private Coupon(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            Boolean published
    ) {
        this.code = sanitizeCode(code);
        this.description = validateDescription(description);
        this.discountValue = validateDiscountValue(discountValue);
        this.expirationDate = validateExpirationDate(expirationDate);
        this.published = published != null && published;
        this.redeemed = false;
        this.deleted = false;
        this.status = resolveInitialStatus(expirationDate);
    }

    public static Coupon create(
            String code,
            String description,
            BigDecimal discountValue,
            Instant expirationDate,
            Boolean published
    ) {
        return new Coupon(code, description, discountValue, expirationDate, published);
    }

    public void delete() {
        if (this.deleted) {
            throw new BusinessException("Coupon already deleted");
        }
        this.deleted = true;
        this.status = CouponStatus.DELETED;
    }

    public void redeem() {
        if (this.deleted) {
            throw new BusinessException("Deleted coupon cannot be redeemed");
        }
        if (isExpired()) {
            throw new BusinessException("Expired coupon cannot be redeemed");
        }
        if (this.redeemed) {
            throw new BusinessException("Coupon already redeemed");
        }
        this.redeemed = true;
        this.status = CouponStatus.REDEEMED;
    }

    public void refreshStatus() {
        if (this.deleted) {
            this.status = CouponStatus.DELETED;
        } else if (this.redeemed) {
            this.status = CouponStatus.REDEEMED;
        } else if (isExpired()) {
            this.status = CouponStatus.EXPIRED;
        } else {
            this.status = CouponStatus.ACTIVE;
        }
    }

    private boolean isExpired() {
        return expirationDate.isBefore(Instant.now());
    }

    private static CouponStatus resolveInitialStatus(Instant expirationDate) {
        return expirationDate.isBefore(Instant.now())
                ? CouponStatus.EXPIRED
                : CouponStatus.ACTIVE;
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

    private static String validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new BusinessException("Description is required");
        }
        return description.trim();
    }

    private static BigDecimal validateDiscountValue(BigDecimal discountValue) {
        if (discountValue == null || discountValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Discount value must be greater than zero");
        }
        return discountValue;
    }

    private static Instant validateExpirationDate(Instant expirationDate) {
        if (expirationDate == null) {
            throw new BusinessException("Expiration date is required");
        }
        return expirationDate;
    }
}
