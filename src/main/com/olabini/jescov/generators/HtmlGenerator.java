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

    private static class FilenameAndCoverage {
        private final String filename;
        private final String coverage;
        public FilenameAndCoverage(String filename, String coverage) {
            this.filename = filename;
            this.coverage = coverage;
        }

        public String getFilename() {
            return this.filename;
        }
        public String getCoverage() {
            return this.coverage;
        }
    }

    private void generateLeftFrame(final CoverageData data) throws IOException {
        STGroup g = new STGroupFile("templates/cobertura-html.stg");
        final ST template = g.getInstanceOf("sourcefiles");

        for(String file : data.getFileNames()) {
            FileCoverage fc = data.getFileCoverageFor(file);
            int valid = fc.getLinesValid();
            int covered = fc.getLinesCovered();
            String percentage;
            if(valid == 0) {
                percentage = "N/A";
            } else {
                percentage = ""+((int)(((double)covered / (double)valid) * 100))+"%";
            }
            template.add("fileAndCoverage", new FilenameAndCoverage(file, percentage));
        }

        intoFile("frame-sourcefiles.html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    template.write(new NoIndentWriter(w));
                }
            });
    }

    private void generateRightFrame(CoverageData data) {
        
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
