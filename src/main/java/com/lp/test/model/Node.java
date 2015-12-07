package com.lp.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public class Node implements Element {

    private int id;

    private String name;

    private Node parent;

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
