package com.onlinepharma.onlinepharma.enums;


public enum Headers {

    LANGUAGE("Accept-Language", "ru");

    public String key;
    public String defValue;

    Headers(String key, String defValue) {
        this.key = key;
        this.defValue = defValue;
    }

    public static Headers findByKey(String key) {
        for (Headers param : values()) {
            if (param.key.equals(key)) {
                return param;
            }
        }
        return null;
    }
}
