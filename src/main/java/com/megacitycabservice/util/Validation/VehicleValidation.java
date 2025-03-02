package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class VehicleValidation {
    public static String validateModel(String model) {
        if (model == null || !model.matches(ValidationConstant.REGEX_MODEL)) {
            return "Invalid model. It should contain only letters, numbers and spaces (3-50 characters).";
        }
        return null;
    }

    public static String validatePlateNumber(String plateNumber) {
        if (plateNumber == null || !plateNumber.matches(ValidationConstant.REGEX_NUMBER_PLATE)) {
            return "Invalid plate number. Format should be PP LLL DDDD (e.g., SP ABC 1234).";
        }
        return null;
    }

    public static String validateNumberOfPassengers(int seats) {
        if (seats < 1 || seats > 16) {
            return "Invalid number of passengers. It should be between 1 and 16.";
        }
        return null;
    }

    public static String validatePricePerKm(double pricePerKm) {
        if (pricePerKm <= 0) {
            return "Invalid price per km. It should be a positive number.";
        }
        return null;
    }

    public static String validateVehicle(String model, String plateNumber, int seats, double pricePerKm) {
        String validationMessage;

        validationMessage = validateModel(model);
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePlateNumber(plateNumber);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateNumberOfPassengers(seats);
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePricePerKm(pricePerKm);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}
