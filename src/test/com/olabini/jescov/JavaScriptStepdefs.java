/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import com.olabini.jescov.console.Runner;

import cucumber.annotation.en.When;

public class JavaScriptStepdefs {
    private CucumberData data;

    public JavaScriptStepdefs(CucumberData data) {
        this.data = data;
    }

    @When("^I execute this JavaScript:$")
    public void I_execute_this_JavaScript(String sourceCode) {
        Runner r = new Runner();
        r.executeSource("<test input>", sourceCode);
        CoverageData cd = r.done();
        data.setCoverageData(cd);
    }
}// JavaScriptStepdefs
