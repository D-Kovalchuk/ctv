package com.ctv.registration.rest;

/**
 * @author Dmitry Kovalchuk
 */
public class Endpoint {

    public static final String TOKEN_PATH = "/tokens";
    public static final String USER_PATH = "/users";
    public static final String BY_ID = "/{id}";
    public static final String PASSWORD = "/{id}/password";

    public static final String OLD_PASSWORD = "old_password";
    public static final String NEW_PASSWORD = "new_password";

    public static final String SIZE_PARAM = "size";
    public static final String PAGE_PARAM = "page";
    public static final String START_PAGE = "0";
    public static final String PAGE_SIZE = "10";

    public static final String X_AUTH_TOKEN = "x-auth-token";

}
