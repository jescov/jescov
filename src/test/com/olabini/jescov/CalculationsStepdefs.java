/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;

import cucumber.annotation.en.Then;

public class CalculationsStepdefs {
    private CucumberData data;

    public CalculationsStepdefs(CucumberData data) {
        this.data = data;
    }

    @Then("^the file branch-rate should be ([\\d.]+)$")
    public void the_file_branch_rate_should_be_(double expectedBranchRate) {
        double branchRate = data.getCoverageData().getFileCoverageFor("<test input>").getBranchRate();
        assertThat(branchRate, is(closeTo(expectedBranchRate, 0.001)));
    }
     
    @Then("^the file branches-covered should be (\\d+)$")
    public void the_file_branches_covered_should_be_(int expectedBranchesCovered) {
        int branchesCovered = data.getCoverageData().getFileCoverageFor("<test input>").getBranchesCovered();
        assertThat(branchesCovered, is(expectedBranchesCovered));
    }
     
    @Then("^the file branches-valid should be (\\d+)$")
    public void the_file_branches_valid_should_be_(int expectedBranches) {
        int branchesValid = data.getCoverageData().getFileCoverageFor("<test input>").getBranchesValid();
        assertThat(branchesValid, is(expectedBranches));
    }
     
    @Then("^the file line-rate should be ([\\d.]+)$")
    public void the_file_line_rate_should_be_(double expectedLineRate) {
        double lineRate = data.getCoverageData().getFileCoverageFor("<test input>").getLineRate();
        assertThat(lineRate, is(closeTo(expectedLineRate, 0.001)));
    }
     
    @Then("^the file lines-covered should be (\\d+)$")
    public void the_file_lines_covered_should_be_(int expectedLinesCovered) {
        int linesCovered = data.getCoverageData().getFileCoverageFor("<test input>").getLinesCovered();
        assertThat(linesCovered, is(expectedLinesCovered));
    }
     
    @Then("^the file lines-valid should be (\\d+)$")
    public void the_file_lines_valid_should_be_(int expectedLines) {
        int linesValid = data.getCoverageData().getFileCoverageFor("<test input>").getLinesValid();
        assertThat(linesValid, is(expectedLines));
    }




    @Then("^the complete branch-rate should be ([\\d.]+)$")
    public void the_complete_branch_rate_should_be_(double expectedBranchRate) {
        double branchRate = data.getCoverageData().getBranchRate();
        assertThat(branchRate, is(closeTo(expectedBranchRate, 0.001)));
    }
     
    @Then("^the complete branches-covered should be (\\d+)$")
    public void the_complete_branches_covered_should_be_(int expectedBranchesCovered) {
        int branchesCovered = data.getCoverageData().getBranchesCovered();
        assertThat(branchesCovered, is(expectedBranchesCovered));
    }
     
    @Then("^the complete branches-valid should be (\\d+)$")
    public void the_complete_branches_valid_should_be_(int expectedBranches) {
        int branchesValid = data.getCoverageData().getBranchesValid();
        assertThat(branchesValid, is(expectedBranches));
    }
     
    @Then("^the complete line-rate should be ([\\d.]+)$")
    public void the_complete_line_rate_should_be_(double expectedLineRate) {
        double lineRate = data.getCoverageData().getLineRate();
        assertThat(lineRate, is(closeTo(expectedLineRate, 0.001)));
    }
     
    @Then("^the complete lines-covered should be (\\d+)$")
    public void the_complete_lines_covered_should_be_(int expectedLinesCovered) {
        int linesCovered = data.getCoverageData().getLinesCovered();
        assertThat(linesCovered, is(expectedLinesCovered));
    }
     
    @Then("^the complete lines-valid should be (\\d+)$")
    public void the_complete_lines_valid_should_be_(int expectedLines) {
        int linesValid = data.getCoverageData().getLinesValid();
        assertThat(linesValid, is(expectedLines));
    }
}
