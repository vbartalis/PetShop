package io.github.vbartalis.petshop.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods that can validate user inputs.
 */
public class InputValidator {

    /**
     * This method validates weather a provided Sting is a valid password or not.
     *
     * A valid password has to contain at least 6 characters and at most 15 characters,
     * at least one digit,
     * at least one upper case alphabet,
     * at least one lower case alphabet,
     * at least one special character which includes @#$%&-+=.
     * It can't contain any white space
     *
     *  https://www.regexlib.com/Search.aspx?k=password&AspxAutoDetectCookieSupport=1
     *
     * @param password The provided String that has to be validated.
     * @return Returns true if the provided string is a valid password, otherwise false.
     */
    public static boolean isPasswordValid(String password)
    {
        String regex = "^([a-zA-Z0-9@#$%^&-+=]{6,15})$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * This method validates weather a provided Sting is a valid username or not.
     *
     * A valid username has to contain at least 5 characters and at most 15 characters,
     * at least one digit,
     * at least one upper case alphabet,
     * at least one lower case alphabet.
     *
     * @param username The provided String that has to be validated.
     * @return Returns true if the provided string is a valid username, otherwise false.
     */
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
