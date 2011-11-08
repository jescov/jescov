/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.io.IOException;

import com.olabini.jescov.CoverageData;

public interface Generator {
    void generate(CoverageData data) throws IOException;
}
