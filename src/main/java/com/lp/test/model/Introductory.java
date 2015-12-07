package com.lp.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Introductory implements Element {

    private com.lp.test.model.introduction introduction;

    public com.lp.test.model.introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(com.lp.test.model.introduction introduction) {
        this.introduction = introduction;
    }
}
