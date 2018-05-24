package com.hlz.util;

public enum SignEnum {
    /**
     * sign_in 签到
     * sign_out签退
     */
    SIGN_IN(0),
    SIGN_OUT(1);
    private Integer value;

    SignEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
