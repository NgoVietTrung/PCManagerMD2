package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String PASSWORD_REGEX = "^([a-zA-Z0-9]{8,})";
    public static final String NAME_REGEX = "^([A-Z]+[a-z]*[ ]?)+$";
    public static final String USERNAME_REGEX = "^[A-z_](\\w|\\.|_){5,31}$";
    public static final String PHONE_REGEX = "^[0][1-9][0-9]{8,9}$";
    public static final String EMAIL_REGEX = "^[a-z][a-z0-9_.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
    public static boolean ispasswordvalid(String password){
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }
    public static boolean isnamevalid(String password){
        return Pattern.compile(NAME_REGEX).matcher(password).matches();
    }
    public static boolean isusernamevalid(String password){
        return Pattern.compile(USERNAME_REGEX).matcher(password).matches();
    }
    public static boolean isphonevalid(String password){
        return Pattern.compile(PHONE_REGEX).matcher(password).matches();
    }
    public static boolean isemailvalid(String password){
        return Pattern.compile(EMAIL_REGEX).matcher(password).matches();
    }
}
