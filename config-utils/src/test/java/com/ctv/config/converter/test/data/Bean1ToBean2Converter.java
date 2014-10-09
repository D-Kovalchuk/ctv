package com.ctv.config.converter.test.data;

import org.springframework.core.convert.converter.Converter;

/**
 * @author Dmitry Kovalchuk
 */
public class Bean1ToBean2Converter implements Converter<Bean1, Bean2> {

    @Override
    public Bean2 convert(Bean1 source) {
        return new Bean2();
    }

}
