package com.lp.test.parser.impl;

import com.lp.test.model.Element;
import com.lp.test.parser.Parser;
import com.lp.test.parser.exception.ParseException;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Parser implementation that uses StAX stream as an internal parser.
 * The StAX stream parser allows to parse the XML document without fully loading it in memory, thus improving
 * performance.
 * The Stream parser has been chosen in favour of the Event parser to guarantee the best performance.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public abstract class AbstractStaxStreamParser<T extends Element> implements Parser<T> {


    protected Map<Integer, T> lookup = new HashMap<>();

    /**
     * Parse the provided {@link InputStream}.
     * <p/>
     * Note that the InputStream is closed after parsing.
     *
     * @param inputStream
     * @return
     * @throws XMLStreamException
     */
    @Override
    public Map<Integer, T> parse(InputStream inputStream) throws ParseException {

        initParser();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;

        try {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        handleStartElement(reader);
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        handleEndElement(reader);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        } finally {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                throw new ParseException(e.getMessage(), e);
            }
            IOUtils.closeQuietly(inputStream);
        }
        return lookup;
    }

    protected void initParser() {
        if (lookup != null) {
            lookup.clear();
        } else {
            lookup = new HashMap<>();
        }
    }

    protected abstract void handleEndElement(XMLStreamReader reader) throws ParseException;

    protected abstract void handleStartElement(XMLStreamReader reader) throws ParseException;
}
