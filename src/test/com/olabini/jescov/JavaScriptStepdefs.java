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
        data.getRunner().executeSource("<test input>", sourceCode);
    }

    @When("^I execute this JavaScript named (.*?):$")
    public void I_execute_this_JavaScript_named(String filename, String sourceCode) {
        data.getRunner().executeSource(filename, sourceCode);
    }
}// JavaScriptStepdefs
