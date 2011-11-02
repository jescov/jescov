/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import com.olabini.jescov.console.Runner;

public class CucumberData {
    private CoverageData coverageData;
    private Runner runner;

    public CoverageData getCoverageData() {
        if(this.coverageData == null) {
            coverageData = this.runner.done();
            this.runner = null;
        }
        return this.coverageData;
    }

    public Runner getRunner() {
        if(this.runner == null) {
            this.runner = new Runner();
        }
        return this.runner;
    }
}// CucumberData
