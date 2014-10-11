package com.ctv.registration.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.util.ClassUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Timur Yarosh
 */
public class LogTracer extends CustomizableTraceInterceptor {

    public static final Logger log = LoggerFactory.getLogger(LogTracer.class);
    private ObjectMapper mapper;

    public LogTracer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private static final Pattern PATTERN = Pattern.compile("\\$\\[\\p{Alpha}+\\]");
    @Override
    protected String replacePlaceholders(String message, MethodInvocation methodInvocation, Object returnValue, Throwable throwable, long invocationTime) {
        Matcher matcher = PATTERN.matcher(message);

        StringBuffer output = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            if (PLACEHOLDER_METHOD_NAME.equals(match)) {
                matcher.appendReplacement(output, Matcher.quoteReplacement(methodInvocation.getMethod().getName()));
            }
            else if (PLACEHOLDER_TARGET_CLASS_NAME.equals(match)) {
                String className = getClassForLogging(methodInvocation.getThis()).getName();
                matcher.appendReplacement(output, Matcher.quoteReplacement(className));
            }
            else if (PLACEHOLDER_TARGET_CLASS_SHORT_NAME.equals(match)) {
                String shortName = ClassUtils.getShortName(getClassForLogging(methodInvocation.getThis()));
                matcher.appendReplacement(output, Matcher.quoteReplacement(shortName));
            }
            else if (PLACEHOLDER_ARGUMENTS.equals(match)) {
                String args = Stream.of(methodInvocation.getArguments()).map(this::toJson).collect(Collectors.joining(",\n"));
                matcher.appendReplacement(output,
                        Matcher.quoteReplacement(args));
            }
            else if (throwable != null && PLACEHOLDER_EXCEPTION.equals(match)) {
                matcher.appendReplacement(output, Matcher.quoteReplacement(throwable.toString()));
            }
            else if (PLACEHOLDER_INVOCATION_TIME.equals(match)) {
                matcher.appendReplacement(output, Long.toString(invocationTime));
            }
            else if (PLACEHOLDER_RETURN_VALUE.equals(match)) {
                appendReturnValue(methodInvocation, matcher, output, returnValue);
            }
            else {
                // Should not happen since placeholders are checked earlier.
                throw new IllegalArgumentException("Unknown placeholder [" + match + "]");
            }
        }
        matcher.appendTail(output);

        return output.toString();
    }

    /**
     * Adds the {@code String} representation of the method return value
     * to the supplied {@code StringBuffer}. Correctly handles
     * {@code null} and {@code void} results.
     * @param methodInvocation the {@code MethodInvocation} that returned the value
     * @param matcher the {@code Matcher} containing the matched placeholder
     * @param output the {@code StringBuffer} to write output to
     * @param returnValue the value returned by the method invocation.
     */
    private void appendReturnValue(
            MethodInvocation methodInvocation, Matcher matcher, StringBuffer output, Object returnValue) {

        if (methodInvocation.getMethod().getReturnType() == void.class) {
            matcher.appendReplacement(output, "void");
        }
        else if (returnValue == null) {
            matcher.appendReplacement(output, "null");
        }
        else {
            matcher.appendReplacement(output, Matcher.quoteReplacement(returnValue.toString()));
        }
    }

    private String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.trace("Can't serialize object");
        }
        return o.getClass().getName();
    }
}
