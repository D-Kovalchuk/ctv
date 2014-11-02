package com.ctv.registration.core.exception;

/**
 * @author Timur Yarosh
 */
public class ResourceNotFoundException extends CoreException {
    public ResourceNotFoundException(ErrorData errorData) {
        super(errorData);
    }
}
