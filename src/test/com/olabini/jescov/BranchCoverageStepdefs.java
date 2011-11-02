/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import java.io.IOException;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasBranchCoverage;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasNegativeBranchCoverage;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasPositiveBranchCoverage;

import cucumber.annotation.en.Then;

import com.olabini.jescov.generators.JsonGenerator;
import com.olabini.jescov.generators.XmlGenerator;

import org.json.simple.JSONValue;

public class BranchCoverageStepdefs {
    private CucumberData data;

    public BranchCoverageStepdefs(CucumberData data) {
        this.data = data;
    }

    @Then("^the negative branch coverage on line (\\d+) should be (\\d+)$")
    public void the_negative_branch_coverage_on_line_should_be_(int line, int expectedCoverage) {
        assertThat(data.getCoverageData(), hasNegativeBranchCoverage(expectedCoverage).onLine(line));
    }
     
    @Then("^the positive branch coverage on line (\\d+) should be (\\d+)$")
    public void the_positive_branch_coverage_on_line_should_be_(int line, int expectedCoverage) {
        assertThat(data.getCoverageData(), hasPositiveBranchCoverage(expectedCoverage).onLine(line));
    }

    @Then("^the branch coverage on line (\\d+) should be (\\d+) on axis (\\d+)$")
    public void the_positive_branch_coverage_on_line_should_be_(int line, int expectedCoverage, int axis) {
        assertThat(data.getCoverageData(), hasBranchCoverage(expectedCoverage).onLine(line).onAxis(axis));
    }

    @Then("^the generated JSON should be:$")
    public void the_generated_JSON_should_be(String expectedJSON) throws IOException {
        StringWriter writer = new StringWriter();
        new JsonGenerator().generate(data.getCoverageData(), writer);

        Object expected = JSONValue.parse(expectedJSON);
        Object real = JSONValue.parse(writer.toString());

        assertThat(real, is(expected));
    }

    @Then("^the generated XML should be:$")
    public void the_generated_XML_should_be(String expectedXML) throws IOException {
        StringWriter writer = new StringWriter();
        new XmlGenerator().generate(data.getCoverageData(), writer);

        assertEquals(expectedXML, writer.toString());
    }
}// BranchCoverageStepdefs
