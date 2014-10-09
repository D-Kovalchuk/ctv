package com.ctv.config.converter.test.data1;

import org.springframework.core.convert.converter.Converter;

/**
 * @author Dmitry Kovalchuk
 */
public class Bean3ToBean4Converter implements Converter<Bean3, Bean4> {

    @Override
    public Bean4 convert(Bean3 source) {
        return new Bean4();
    }

}
