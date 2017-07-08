package com.sgkhmjaes.jdias.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String SMALL_PREFIX = "thumb_small_";
    public static final String MEDIUM_PREFIX = "thumb_medium_";
    public static final String LARGE_PREFIX = "thumb_large_";
    public static final String SCALED_FULL_PREFIX = "scaled_full_";

    private Constants() {
    }
}
