package com.lp.test.parser;

import com.lp.test.model.Element;
import com.lp.test.parser.exception.ParseException;

import java.io.InputStream;
import java.util.Map;

/**
 * A generic parser for {@link Element} objects.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public interface Parser<T extends Element> {

    /**
     * Parse the provided {@link InputStream} and returns a lookup table.
     *
     * @param inputStream the InputStream to parse.
     * @return the lookup table
     * @throws ParseException if an error occurs during parsing
     */
    Map<Integer, T> parse(InputStream inputStream) throws ParseException;

}
