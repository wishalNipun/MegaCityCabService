package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class CustomerValidation {

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

    public static String validateContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.matches(ValidationConstant.REGEX_MOBILE)) {
            return "Invalid contact number. Contact number should start with 0 or 7, or +94, and be followed by 9 or 10 digits.";
        }
        return null;
    }

    public static String validateUsername(String username) {
        if (username == null || !username.matches(ValidationConstant.REGEX_USERNAME)) {
            return "Invalid username. Username should contain alphanumeric characters, underscores, or hyphens and be between 3 to 20 characters.";
        }
        return null;
    }

    public static String validatePassword(String password) {
        if (password == null || !password.matches(ValidationConstant.REGEX_PASSWORD)) {
            return "Invalid password. Password should contain alphanumeric characters, @, #, or _ and be between 8 to 15 characters.";
        }
        return null;
    }


    public static String validateCustomer(String name, String nic, String address, String contactNumber, String username, String password) {
        String validationMessage;

        validationMessage = validateName(name);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateNIC(nic);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateAddress(address);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateContactNumber(contactNumber);
        if (validationMessage != null) return validationMessage;

        validationMessage = validateUsername(username);
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePassword(password);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}


