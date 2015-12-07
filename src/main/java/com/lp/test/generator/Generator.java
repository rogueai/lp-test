package com.lp.test.generator;

import com.lp.test.model.Destination;
import com.lp.test.model.Node;

/**
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public interface Generator {

    void generate(Node node, Destination destination);
}
