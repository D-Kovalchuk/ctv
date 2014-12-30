package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public class ResourceNotFoundException extends CoreException {
    public ResourceNotFoundException(ErrorCode errorData) {
        super(errorData);
    }
}
