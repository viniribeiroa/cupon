/**
 * Autor: VINICIUS
 * Data: 8 de mar. de 2026
 * Descrição: TODO
 */
package com.stormdev.common.constants;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String COUPON_NOT_FOUND = "Coupon not found";

    public static final String COUPON_ALREADY_DELETED =
            "Coupon already deleted";

    public static final String COUPON_ALREADY_REDEEMED =
            "Coupon already redeemed";

    public static final String COUPON_CODE_ALREADY_EXISTS =
            "Coupon code already exists";

    public static final String COUPON_CODE_REQUIRED =
            "Coupon code is required";

    public static final String COUPON_CODE_INVALID_LENGTH =
            "Coupon code must contain exactly 6 alphanumeric characters";

    public static final String COUPON_EXPIRED =
            "Expired coupon cannot be redeemed";

    // Validation errors
    public static final String DESCRIPTION_REQUIRED =
            "Description is required";

    public static final String DISCOUNT_INVALID =
            "Discount value must be greater than zero";

    public static final String EXPIRATION_DATE_REQUIRED =
            "Expiration date is required";

}
