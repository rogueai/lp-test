package com.lp.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Node implements Element {

    @XmlAttribute(name = "atlas_node_id")
    private int id;

    @XmlElement(name = "node_name")
    private String name;

    @XmlTransient
    private Node parent;

    @XmlElement(name = "node")
    private List<Node> children = new ArrayList<Node>();

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
