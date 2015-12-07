package com.lp.test.parser.impl;

import com.lp.test.model.Node;
import com.lp.test.parser.exception.ParseException;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Stack;

/**
 * Taxonomy parser using StAX Cursor APIs to parse the taxonomy xml.
 * <p/>
 * The implementation maintains a Stack of {@link Node} to construct parent-child relationships in existence between nodes.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class TaxonomyStaxStreamParser extends AbstractStaxStreamParser<Node> {


    private static final String ELEMENT__TAXONOMY = "taxonomy";
    private static final String ELEMENT__TAXONOMY_NAME = "taxonomy_name";
    private static final String ELEMENT__NODE = "node";
    private static final String ELEMENT__NODE_NAME = "node_name";
    private static final String ATTRIBUTE__ATLAS_NODE_ID = "atlas_node_id";
    private static final int ROOT_NODE_ID = 0;

    private Stack<Node> stack = new Stack<>();

    @Override
    protected void initParser() {
        super.initParser();
        if (stack == null) {
            stack = new Stack<>();
        } else {
            stack.clear();
        }
    }

    @Override
    protected void handleStartElement(XMLStreamReader reader) throws ParseException {
        String name = reader.getLocalName().toLowerCase();
        if (name != null) {
            switch (name) {
                case ELEMENT__TAXONOMY:
                    createNode(ROOT_NODE_ID);
                    break;
                case ELEMENT__TAXONOMY_NAME:
                    stack.peek().setName(parseNodeText(reader));
                    break;
                case ELEMENT__NODE:
                    int id = Integer.parseInt(reader.getAttributeValue("", ATTRIBUTE__ATLAS_NODE_ID));
                    createNode(id);
                    break;
                case ELEMENT__NODE_NAME:
                    stack.peek().setName(parseNodeText(reader));
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void handleEndElement(XMLStreamReader reader) throws ParseException {
        String name = reader.getLocalName();
        if (name != null) {
            switch (name) {
                case "node":
                    // set child relationship
                    Node child = stack.pop();
                    if (!stack.isEmpty()) {
                        stack.peek().getChildren().add(child);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Read CHARACTERS events from the reader, until it reaches the next END_ELEMENT tag.
     *
     * @param reader
     * @return
     */
    private String parseNodeText(XMLStreamReader reader) {
        try {
            StringBuilder text = new StringBuilder();
            do {
                if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                    text.append(reader.getText());
                }
                reader.next();
            } while (reader.hasNext() && reader.getEventType() != XMLStreamConstants.END_ELEMENT);

            return StringUtils.trimToEmpty(text.toString());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Node createNode(int nodeId) {
        Node node = new Node();
        node.setId(nodeId);
        // set parent relationship and push the node to the stack
        // Note: taxonomy is the root and has no parent
        if (!stack.isEmpty()) {
            node.setParent(stack.peek());
            lookup.put(nodeId, node);
        }
        stack.push(node);
        return node;
    }

}
