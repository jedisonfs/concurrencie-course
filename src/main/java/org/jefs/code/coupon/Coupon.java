package org.jefs.code.coupon;

import lombok.Data;

@Data
public class Coupon {

    private String code;
    private int discountPercentage;
    private String  customerName;

    public Coupon(String code, int discountPercentage) {
        this.code = code;
        this.discountPercentage = discountPercentage;
    }
}
