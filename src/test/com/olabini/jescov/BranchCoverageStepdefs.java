/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasBranchCoverage;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasNegativeBranchCoverage;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasPositiveBranchCoverage;

import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import com.olabini.jescov.generators.JsonGenerator;
import com.olabini.jescov.generators.JsonIngester;
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
        new JsonGenerator(writer).generate(data.getCoverageData());

        Object expected = JSONValue.parse(expectedJSON);
        Object real = JSONValue.parse(writer.toString());

        assertThat(real, is(expected));
    }

    @Then("^the generated XML should be:$")
    public void the_generated_XML_should_be(String expectedXML) throws IOException {
        StringWriter writer = new StringWriter();
        new XmlGenerator(writer).generate(data.getCoverageData());

        assertEquals(expectedXML, writer.toString());
    }

    @When("^I ingest this JSON:$")
    public void I_ingest_this_JSON(String jsonToIngest) throws IOException {
        CoverageData cd = new JsonIngester().ingest(new StringReader(jsonToIngest));
        data.setCoverageData(cd);
    }


    @Then("^the internal structures should be correct$")
    public void the_internal_structures_should_be_correct() {
        CoverageData cd = data.getCoverageData();
        FileCoverage fc = cd.getFileCoverageFor("<test input>");
        LineCoverage lc;
        Collection<BranchCoverage> bcs;

        lc = fc.getLineCoverageFor(1);
        bcs = fc.getBranchCoverageFor(1);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(2);
        bcs = fc.getBranchCoverageFor(2);
        assertThat(lc.getHits(), is(8));
        assertThat(bcs.size(), is(1));
        assertThat(bcs.iterator().next().getBranches()[0], is(4));
        assertThat(bcs.iterator().next().getBranches()[1], is(4));

        lc = fc.getLineCoverageFor(3);
        bcs = fc.getBranchCoverageFor(3);
        assertThat(lc.getHits(), is(4));
        assertThat(bcs.size(), is(2));
        Iterator<BranchCoverage> iter = bcs.iterator();
        BranchCoverage first = iter.next();
        BranchCoverage second = iter.next();
        assertThat(first.getBranches()[0], is(1));
        assertThat(first.getBranches()[1], is(1));
        assertThat(second.getBranches()[0], is(2));
        assertThat(second.getBranches()[1], is(2));

        lc = fc.getLineCoverageFor(5);
        bcs = fc.getBranchCoverageFor(5);
        assertThat(lc.getHits(), is(4));
        assertThat(bcs.size(), is(2));
        iter = bcs.iterator();
        first = iter.next();
        second = iter.next();
        assertThat(first.getBranches()[0], is(1));
        assertThat(first.getBranches()[1], is(1));
        assertThat(second.getBranches()[0], is(2));
        assertThat(second.getBranches()[1], is(2));

        lc = fc.getLineCoverageFor(8);
        bcs = fc.getBranchCoverageFor(8);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(9);
        bcs = fc.getBranchCoverageFor(9);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(10);
        bcs = fc.getBranchCoverageFor(10);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(11);
        bcs = fc.getBranchCoverageFor(11);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(12);
        bcs = fc.getBranchCoverageFor(12);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(13);
        bcs = fc.getBranchCoverageFor(13);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(14);
        bcs = fc.getBranchCoverageFor(14);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));

        lc = fc.getLineCoverageFor(15);
        bcs = fc.getBranchCoverageFor(15);
        assertThat(lc.getHits(), is(1));
        assertThat(bcs, is(nullValue()));
    }
}// BranchCoverageStepdefs
