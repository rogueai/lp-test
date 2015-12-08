package com.lp.test.generator;

import com.lp.test.model.Destination;
import com.lp.test.model.Node;

/**
 * A {@link Generator} provides the functionality of transforming the given input to a target format.
 *
 * @author Massimo Zugno <d3k41n@gmail.com>
 */
public interface Generator {

    /**
     * Generates output data for the given {@link Node} and {@link Destination}
     *
     * @param node        the input {@link Node}
     * @param destination the input {@link Destination}
     */
    void generate(Node node, Destination destination);
}
