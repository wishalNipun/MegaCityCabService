package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class BillValidation {
    public static String validateTax(double taxP) {
        if (taxP < 0 || taxP > 100) {
            return "Invalid tax percentage. Tax must be between 0 and 100.";
        }
        return null;
    }

    public static String validateDiscount(double discount) {
        if (!String.valueOf(discount).matches(ValidationConstant.REGEX_AMOUNT)) {
            return "Invalid discount. Discount must be a positive number with up to 2 decimal places.";
        }
        return null;
    }


    public static String validateBill(double taxP, double discount) {
        String validationMessage;

        validationMessage = validateTax(taxP);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateDiscount(discount);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}
