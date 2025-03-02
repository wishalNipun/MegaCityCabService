package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class BookingValidation {

    public static String validateLocation(String location) {
        if (location == null || !location.matches(ValidationConstant.REGEX_LOCATION)) {
            return "Invalid location. It should contain alphanumeric characters, commas, periods, spaces, or hyphens and be between 3 to 100 characters.";
        }
        return null;
    }


    public static String validateDistance(double distanceKm) {
        if (distanceKm <= 0) {
            return "Invalid distance. Distance must be a positive number.";
        }
        return null;
    }


    public static String validateFee(double fee) {
        if (!String.valueOf(fee).matches(ValidationConstant.REGEX_AMOUNT)) {
            return "Invalid fee. Fee must be a positive number with up to 2 decimal places.";
        }
        return null;
    }


    public static String validateBooking(String pickupLocation, String dropLocation, double distanceKm, double fee) {
        String validationMessage;

        validationMessage = validateLocation(pickupLocation);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateLocation(dropLocation);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateDistance(distanceKm);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateFee(fee);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}
