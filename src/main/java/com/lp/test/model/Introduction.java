package com.lp.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Introduction implements Element{

    private String overview;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
