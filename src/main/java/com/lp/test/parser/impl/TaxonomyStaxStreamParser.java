package com.lp.test.parser.impl;

import com.lp.test.model.Node;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Stack;

/**
 * Taxonomy parser uses  StAX Cursor APIs to parse the taxonomy xml.
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
    protected void handleStartElement(XMLStreamReader reader) throws XMLStreamException {
        String name = reader.getLocalName();
        if (name != null) {
            switch (name) {
                case ELEMENT__TAXONOMY:
                    createNode(ROOT_NODE_ID);
                    break;
                case ELEMENT__TAXONOMY_NAME:
                    reader.next();
                    stack.peek().setName(reader.getText());
                    break;
                case ELEMENT__NODE:
                    int id = Integer.parseInt(reader.getAttributeValue("", ATTRIBUTE__ATLAS_NODE_ID));
                    createNode(id);
                    break;
                case ELEMENT__NODE_NAME:
                    reader.next();
                    stack.peek().setName(reader.getText());
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void handleEndElement(XMLStreamReader reader) throws XMLStreamException {
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

    private Node createNode(int nodeId) {
        Node node = new Node();
        node.setId(nodeId);
        // set parent relationship and push the node to the stack
        // Note: taxonomy is the root has no parent
        if (!stack.isEmpty()) {
            node.setParent(stack.peek());
            lookup.put(nodeId, node);
        }
        stack.push(node);
        return node;
    }

    @Override
    protected void initParser() {
        super.initParser();
        if (stack == null) {
            stack = new Stack<>();
        } else {
            stack.clear();
        }
    }
}
