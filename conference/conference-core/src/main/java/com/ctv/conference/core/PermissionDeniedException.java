package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public class PermissionDeniedException extends CoreException {

    public PermissionDeniedException(ErrorCode errorData) {
        super(errorData);
    }

}
