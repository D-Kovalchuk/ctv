package com.ctv.registration.rest;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
* @author Dmitry Kovalchuk
*/
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "username";

}
