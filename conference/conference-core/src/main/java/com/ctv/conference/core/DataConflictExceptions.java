package com.ctv.conference.core;

import com.ctv.shared.model.ErrorCode;

/**
 * @author Dmitry Kovalchuk
 */
public class DataConflictExceptions extends CoreException {

    public DataConflictExceptions(ErrorCode errorData) {
        super(errorData);
    }

}
