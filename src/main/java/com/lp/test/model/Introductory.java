package com.lp.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Introductory implements Element {

    private Introduction introduction;

    public Introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }
}
