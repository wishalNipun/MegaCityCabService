package com.megacitycabservice.util;

public class ValidationConstant {
    public static final String REGEX_NIC = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
    public static final String REGEX_MOBILE = "^(?:7|0|(?:\\+94))[0-9]{9,10}$";
    public static final String REGEX_NAME = "^[a-zA-Z ]{3,50}$";
    public static final String REGEX_Number_PLATE = "^([A-Z]{1,2})([A-Z]{1,3})([0-9]{4}(?<!0{4}))";
    public static final String REGEX_LOCATION ="^[a-zA-Z0-9,.\s-]{3,100}$";
    public static final String REGEX_AMOUNT ="^[+]?[0-9]{1,12}(\\.[0-9]{1,2})?$";
    public static final String REGEX_USERNAME ="^[a-zA-Z0-9_-]{3,20}$";
    public static final String REGEX_PASSWORD ="^[a-zA-Z0-9@#_-]{5,10}$";
}
