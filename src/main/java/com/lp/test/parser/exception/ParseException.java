package com.lp.test.parser.exception;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class ParseException extends Exception {


    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
