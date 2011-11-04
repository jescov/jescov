/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.io.*;

import java.util.*;

import com.olabini.jescov.CoverageData;
import com.olabini.jescov.FileCoverage;
import com.olabini.jescov.LineCoverage;
import com.olabini.jescov.BranchCoverage;

import org.stringtemplate.v4.*;

public class HtmlGenerator {
    private final static String DEFAULT_DIRECTORY = "coverage-report";

    private final String outputDirectory;
    public HtmlGenerator() {
        this.outputDirectory = DEFAULT_DIRECTORY;
    }

    public void generate(CoverageData data) throws IOException {
        ensureOutputDirectory();
        copyStylesheets();
        copyImages();
        copyJavaScript();
        copyIndex();

        generateLeftFrame(data);
        generateRightFrame(data);
        generateAllFiles(data);
    }

    private void ensureOutputDirectory() throws IOException {
        ensure(outputDirectory);
    }

    private void copyStylesheets() throws IOException {
        ensure(outputDirectory + "/css");
        copy("css/help.css");
        copy("css/main.css");
        copy("css/sortabletable.css");
        copy("css/source-viewer.css");
        copy("css/tooltip.css");
    }

    private void copyImages() throws IOException {
        ensure(outputDirectory + "/images");
        copy("images/blank.png");
        copy("images/downsimple.png");
        copy("images/upsimple.png");
    }

    private void copyJavaScript() throws IOException {
        ensure(outputDirectory + "/js");
        copy("js/customsorttypes.js");
        copy("js/popup.js");
        copy("js/sortabletable.js");
        copy("js/stringbuilder.js");
    }

    private void copyIndex() throws IOException {
        copy("index.html");
    }

    private interface WriteAction {
        void write(Writer w) throws IOException;
    }

    private void intoFile(String fileName, WriteAction wa) throws IOException {
        File f = new File(outputDirectory + "/" + fileName);
        f.getParentFile().mkdirs();
        FileWriter fw = new FileWriter(f);
        wa.write(fw);
        fw.close();
    }

    private void generateLeftFrame(final CoverageData data) throws IOException {
        STGroup g = new STGroupFile("templates/cobertura-html.stg");
        final ST template = g.getInstanceOf("sourcefiles");

        for(String file : data.getFileNames()) {
            FileCoverage fc = data.getFileCoverageFor(file);
            template.add("fileAndCoverage", new FilesAndRelatedInformation(true, file, fc.getLinesValid(), fc.getLinesCovered(), fc.getBranchesValid(), fc.getBranchesCovered()));
        }

        intoFile("frame-sourcefiles.html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    template.write(new NoIndentWriter(w));
                }
            });
    }


    private static class FilesAndRelatedInformation {
        private final boolean link;
        private final String filename;
        private final int validLines;
        private final int coveredLines;
        private final int validBranches;
        private final int coveredBranches;

        public FilesAndRelatedInformation(boolean link, String filename, int validLines, int coveredLines, int validBranches, int coveredBranches) {
            this.link = link;
            this.filename = filename;
            this.validLines = validLines;
            this.coveredLines = coveredLines;
            this.validBranches = validBranches;
            this.coveredBranches = coveredBranches;
        }

        public boolean getLink() {
            return this.link;
        }

        public String getFilename() {
            return this.filename;
        }

        public String getLinesCoveragePercent() {
            return coveragePercent(coveredLines, validLines);
        }

        public String getBranchesCoveragePercent() {
            return coveragePercent(coveredBranches, validBranches);
        }

        public String getLinesCoverageWidth() {
            return coverageWidth(coveredLines, validLines);
        }

        public String getBranchesCoverageWidth() {
            return coverageWidth(coveredBranches, validBranches);
        }

        public String getLinesCoverageFraction() {
            return coverageFraction(coveredLines, validLines);
        }

        public String getBranchesCoverageFraction() {
            return coverageFraction(coveredBranches, validBranches);
        }

        private String coveragePercent(int covered, int valid) {
            if(valid == 0) {
                return "N/A";
            } else {
                return ""+((int)(((double)covered / (double)valid) * 100))+"%";
            }
        }

        private String coverageWidth(int covered, int valid) {
            if(valid == 0) {
                return "0";
            } else {
                return ""+((int)(((double)covered / (double)valid) * 100));
            }
        }

        private String coverageFraction(int covered, int valid) {
            if(valid == 0) {
                return "N/A";
            } else {
                return "" + covered + "/" + valid;
            }
        }
    }


    private void generateRightFrame(CoverageData data) throws IOException {
        STGroup g = new STGroupFile("templates/cobertura-html.stg");
        final ST template = g.getInstanceOf("summary");

        template.add("fileAndCoverage", new FilesAndRelatedInformation(false, "All Files", data.getLinesValid(), data.getLinesCovered(), data.getBranchesValid(), data.getBranchesCovered()));
        for(String file : data.getFileNames()) {
            FileCoverage fc = data.getFileCoverageFor(file);
            template.add("fileAndCoverage", new FilesAndRelatedInformation(true, file, fc.getLinesValid(), fc.getLinesCovered(), fc.getBranchesValid(), fc.getBranchesCovered()));
        }

        intoFile("frame-summary.html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    template.write(new NoIndentWriter(w));
                }
            });
        
    }

    private void generateAllFiles(CoverageData data) {
        
    }

    private void ensure(String dir) throws IOException {
        new File(dir).mkdirs();
    }

    private void copy(String file) throws IOException {
        byte[] buffer = new byte[2048];
        int read = 0;
        FileOutputStream out = new FileOutputStream(new File(outputDirectory, file));
        InputStream is = HtmlGenerator.class.getResourceAsStream("/templates/" + file);
        while((read = is.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        is.close();
        out.close();
    }
}
