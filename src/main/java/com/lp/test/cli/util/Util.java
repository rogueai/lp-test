package com.lp.test.cli.util;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class Util {

    public static void printf(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
