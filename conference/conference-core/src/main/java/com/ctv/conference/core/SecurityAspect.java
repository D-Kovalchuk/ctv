package com.ctv.conference.core;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author Dmitry Kovalchuk
 */
@Aspect
public class SecurityAspect {

    @AfterThrowing(pointcut = "@annotation(com.ctv.conference.core.Secure)", throwing = "ex")
    public void reTrow(PermissionDeniedException ex) {
        throw new AccessDeniedException(ex.getMessage(), ex);
    }

}
