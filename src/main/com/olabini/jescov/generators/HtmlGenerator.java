/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov.generators;

import java.io.*;

import java.util.*;

import com.olabini.jescov.Configuration;
import com.olabini.jescov.CoverageData;
import com.olabini.jescov.FileCoverage;
import com.olabini.jescov.LineCoverage;
import com.olabini.jescov.BranchCoverage;

import org.stringtemplate.v4.*;

public class HtmlGenerator implements Generator {
    private final String outputDirectory;
    private final String sourceDirectory;

    public HtmlGenerator(Configuration configuration) {
        this.outputDirectory = configuration.getHtmlOutputDir();
        this.sourceDirectory = configuration.getSourceDirectory();
    }

    public void generate(CoverageData data) throws IOException {
        ensureOutputDirectory();
        copyStylesheets();
        copyImages();
        copyJavaScript();
        copyIndex();

        generateSummaryFrames(data);
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

    private void generateSummaryFrames(final CoverageData data) throws IOException {
        STGroup g = new STGroupFile("templates/cobertura-html.stg");
        final ST templateLeft = g.getInstanceOf("sourcefiles");
        final ST templateRight = g.getInstanceOf("summary");

        templateRight.add("fileAndCoverage", new FilesAndRelatedInformation(false, "All Files", data.getLinesValid(), data.getLinesCovered(), data.getBranchesValid(), data.getBranchesCovered()));
        for(String file : data.getFileNames()) {
            FileCoverage fc = data.getFileCoverageFor(file);
            FilesAndRelatedInformation fari = new FilesAndRelatedInformation(true, file, fc.getLinesValid(), fc.getLinesCovered(), fc.getBranchesValid(), fc.getBranchesCovered());
            templateLeft.add("fileAndCoverage", fari);
            templateRight.add("fileAndCoverage", fari);
        }

        intoFile("frame-sourcefiles.html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    templateLeft.write(new NoIndentWriter(w));
                }
            });

        intoFile("frame-summary.html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    templateRight.write(new NoIndentWriter(w));
                }
            });
    }

    private void generateAllFiles(CoverageData data) throws IOException {
        STGroup g = new STGroupFile("templates/cobertura-html.stg");

        for(String file : data.getFileNames()) {
            final ST template = g.getInstanceOf("file");
            generateFile(template, file, data.getFileCoverageFor(file));
        }
    }

    private void generateFile(final ST template, String file, FileCoverage fc) throws IOException {
        template.add("name", file);
        BufferedReader r = new BufferedReader(new FileReader(new File(sourceDirectory, file)));
        String line;
        int lineNumber = 0;
        while((line = r.readLine()) != null) {
            lineNumber++;
            LineCoverage lc = fc.getLineCoverageFor(lineNumber);
            Collection<int[]> branches = new ArrayList<int[]>();
            Collection<BranchCoverage> bcs = fc.getBranchCoverageFor(lineNumber);
            if(bcs != null) {
                for(BranchCoverage bc : fc.getBranchCoverageFor(lineNumber)) {
                    branches.add(bc.getBranches());
                }
            }
            template.add("line", new LineInfo(lineNumber, line, lc == null ? -1 : lc.getHits(), branches));
        }
        r.close();

        intoFile(file.replaceAll("/", ".") + ".html", new WriteAction() {
                public void write(Writer w) throws IOException {
                    template.write(new NoIndentWriter(w));
                }
            });
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
