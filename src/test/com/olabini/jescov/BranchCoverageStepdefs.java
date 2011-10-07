/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasNegativeBranchCoverage;
import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasPositiveBranchCoverage;

import cucumber.annotation.en.Then;

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
}// BranchCoverageStepdefs
