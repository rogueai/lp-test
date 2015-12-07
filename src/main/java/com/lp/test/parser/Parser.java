package com.lp.test.parser;

import com.lp.test.model.Element;

import javax.xml.stream.XMLStreamException;
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
     * @param inputStream
     * @return
     * @throws XMLStreamException
     */
    Map<Integer, T> parse(InputStream inputStream) throws XMLStreamException;

}
