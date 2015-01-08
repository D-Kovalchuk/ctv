package com.ctv.conference.rest.api;

/**
 * @author Dmitry Kovalchuk
 */
public class Endpoint {

    public static final String CONFERENCE = "/conference";
    public static final String BY_ID = "/{id}";

    public static final String MEETUP_BY_ID = CONFERENCE + "/{confId}/meetup/{meetId}";

    public static final String SIZE_PARAM = "size";
    public static final String PAGE_PARAM = "page";
    public static final String START_PAGE = "0";
    public static final String PAGE_SIZE = "10";

    public static final String X_AUTH_TOKEN = "x-auth-token";

}
