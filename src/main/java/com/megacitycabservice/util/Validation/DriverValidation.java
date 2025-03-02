package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class DriverValidation {
    public static String validateName(String name) {
        if (name == null || !name.matches(ValidationConstant.REGEX_NAME)) {
            return "Invalid name. Name should contain only letters and spaces, and be between 3 to 50 characters.";
        }
        return null;
    }

    public static String validateNIC(String nic) {
        if (nic == null || !nic.matches(ValidationConstant.REGEX_NIC)) {
            return "Invalid NIC. NIC should either be 9 digits followed by x or X or v or V, or 12 digits.";
        }
        return null;
    }



    public static String validateAddress(String address) {
        if (address == null || !address.matches(ValidationConstant.REGEX_LOCATION)) {
            return "Invalid address. Address should contain alphanumeric characters, commas, periods, spaces, or hyphens and be between 3 to 100 characters.";
        }
        return null;
    }

    public static String validateLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || !licenseNumber.matches(ValidationConstant.REGEX_LICENSE_NUMBER)) {
            return "Invalid license number. It should have 7-9 digits (old format) or start with 1-2 letters followed by 7 digits (new format).";
        }
        return null;
    }

    public static String validateContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.matches(ValidationConstant.REGEX_MOBILE)) {
            return "Invalid contact number. Contact number should start with 0 or 7, or +94, and be followed by 9 or 10 digits.";
        }
        return null;
    }



    public static String validateDriver(String name, String nic, String address, String licenseNumber, String contactNumber) {
        String validationMessage;

        validationMessage = validateName(name);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateNIC(nic);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateAddress(address);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateLicenseNumber(licenseNumber);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateContactNumber(contactNumber);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}
