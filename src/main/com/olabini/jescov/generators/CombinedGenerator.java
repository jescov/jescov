/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.util.List;
import static java.util.Arrays.asList;

import java.io.IOException;

import com.olabini.jescov.CoverageData;

public class CombinedGenerator implements Generator {
    private final List<Generator> generators;
    public CombinedGenerator(Generator... generators) {
        this.generators = asList(generators);
    }

    public void generate(CoverageData data) throws IOException {
        for(Generator g : generators) {
            g.generate(data);
        }
    }
}
