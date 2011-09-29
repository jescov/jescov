package com.olabini.jescov;

import org.junit.Test;

import static com.olabini.jescov.matchers.BranchCoverageMatcher.hasBranchCoverage;
import static org.hamcrest.MatcherAssert.assertThat;

public class BranchCoverageTest {

    private static final String TERNERARY_BRANCH_SOURCE_1 =
            "function t1(res) {\n" +
            "  return res ? 1 : 2;\n" +
            "}\n";

    private static final String CALL_T1_TRUE =
            "t1(true);\n";

    private static final String CALL_T1_FALSE =
            "t1(false);\n";

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatIsNeverHit() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1,
          hasBranchCoverage(0, 0).onLine(2)
        );
    }

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatOnlyHitsTheNegativeBranch() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1 +
                  CALL_T1_FALSE
                ,
          hasBranchCoverage(1, 0).onLine(2)
        );
    }

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatOnlyHitsTheNegativeBranchMoreThanOnce() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1 +
                  CALL_T1_FALSE +
                  CALL_T1_FALSE +
                  CALL_T1_FALSE
                ,
          hasBranchCoverage(3, 0).onLine(2)
        );
    }

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatOnlyHitsThePositiveBranch() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1 +
                  CALL_T1_TRUE
                ,
          hasBranchCoverage(0, 1).onLine(2)
        );
    }

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatOnlyHitsThePositiveBranchMoreThanOnce() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1 +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE
                ,
          hasBranchCoverage(0, 5).onLine(2)
        );
    }

    @Test
    public void countsSimpleBranchCoverageForATernaryExpressionThatHitsBothNegativeAndPositiveBranches() {
        assertThat(
          TERNERARY_BRANCH_SOURCE_1 +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE +
                  CALL_T1_FALSE +
                  CALL_T1_TRUE +
                  CALL_T1_TRUE
                ,
          hasBranchCoverage(1, 4).onLine(2)
        );
    }
}
