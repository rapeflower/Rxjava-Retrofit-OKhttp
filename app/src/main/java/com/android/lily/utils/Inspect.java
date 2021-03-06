package com.android.lily.utils;

/**
 * @Author rape flower
 * @Date 2018-04-23 14:50
 * @Describe 检查工具类
 * <p>
 * Provides general assert utils, which are stripped by Android SDK on
 * compile/runtime, to work on release builds
 * </p>
 */
public class Inspect {

    private Inspect() {
    }

    /**
     * Will throw AssertionError, if expression is not true
     *
     * @param expression    result of your asserted condition
     * @param failedMessage message to be included in error log
     * @throws java.lang.AssertionError
     */
    public static void asserts(final boolean expression, final String failedMessage) {
        if (!expression) {
            throw new AssertionError(failedMessage);
        }
    }

    /**
     * Will throw IllegalArgumentException if provided object is null on runtime
     *
     * @param argument object that should be asserted as not null
     * @param name     name of the object asserted
     * @throws java.lang.IllegalArgumentException
     */
    public static <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " should not be null!");
        }
        return argument;
    }
}
