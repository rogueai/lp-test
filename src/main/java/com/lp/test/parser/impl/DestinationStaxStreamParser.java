package com.lp.test.parser.impl;

import com.lp.test.model.Destination;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Destinations parser uses StAX Cursor APIs to parse the destinations xml.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class DestinationStaxStreamParser extends AbstractStaxStreamParser<Destination> {

    private static final String ELEMENT__DESTINATION = "destination";

    private Unmarshaller unmarshaller;

    @Override
    protected void handleStartElement(XMLStreamReader reader) throws XMLStreamException {
        // JAXB moves the cursor to the next event after the END_ELEMENT tag. if it's another start
        // element, we have to try to unmarshall again since it could be another Destination
        while (isDestinationStartElement(reader)) {
            parseDestination(reader);
        }
    }

    private void parseDestination(XMLStreamReader reader) {
        try {
            // use JAXB to unmarshall the destination object,
            Unmarshaller unmarshaller = createUnmarshaller();
            if (unmarshaller != null) {
                Destination destination = unmarshaller.unmarshal(reader, Destination.class).getValue();
                if (destination != null) {
                    lookup.put(destination.getId(), destination);
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private boolean isDestinationStartElement(XMLStreamReader reader) {
        return reader.getEventType() == XMLStreamConstants.START_ELEMENT && ELEMENT__DESTINATION.equalsIgnoreCase(reader.getLocalName());
    }

    private Unmarshaller createUnmarshaller() throws JAXBException {
        if (unmarshaller == null) {
            JAXBContext context = JAXBContext.newInstance(Destination.class);
            unmarshaller = context.createUnmarshaller();
        }
        return unmarshaller;
    }

    @Override
    protected void handleEndElement(XMLStreamReader reader) {
        System.out.println();
    }

}
