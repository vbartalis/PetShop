package io.github.vbartalis.petshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private InputValidator() {}

    public static boolean isPasswordValid(String password)
    {
//        It contains at least 8 characters and at most 15 characters.
//        It contains at least one digit.
//        It contains at least one upper case alphabet.
//        It contains at least one lower case alphabet.
//        It contains at least one special character which includes @#$%&-+=.
//        It can't contain any white space.
//        https://www.regexlib.com/Search.aspx?k=password&AspxAutoDetectCookieSupport=1

        String regex = "^([a-zA-Z0-9@#$%^&-+=]{6,15})$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }

    public static boolean isUsernameValid(String username) {
        String regex = "^([a-zA-Z0-9]{5,15})$";
        Pattern p = Pattern.compile(regex);

        if (username == null) {
            return false;
        }

        if (username.equals("anonymousUser")) {
            return false;
        }

        Matcher m = p.matcher(username);

        return m.matches();
    }

}
