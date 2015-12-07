package com.lp.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Destination implements Element {

    @XmlAttribute(name = "atlas_id")
    private int id;
    @XmlAttribute(name = "title")
    private String title;
    private Introductory introductory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Introductory getIntroductory() {
        return introductory;
    }

    public void setIntroductory(Introductory introductory) {
        this.introductory = introductory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
