package com.megacitycabservice.util.Validation;

import com.megacitycabservice.util.ValidationConstant;

public class UserValidation {
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

    public static String validateUser(String username, String password) {
        String validationMessage;

        validationMessage = validateUsername(username);
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePassword(password);
        if (validationMessage != null) return validationMessage;

        return null;
    }
}
