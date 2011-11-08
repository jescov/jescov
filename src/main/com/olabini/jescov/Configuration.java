/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package com.olabini.jescov;

import com.olabini.jescov.generators.Generator;

public class Configuration {
    public static interface Ignore {
        boolean allow(String filename);
    }

    public static class Allow implements Ignore {
        public boolean allow(String filename) {
            return true;
        }
    }

    public static class File implements Ignore {
        private final String filename;
        public File(String filename) {
            this.filename = filename;
        }

        public boolean allow(String filename) {
            return !this.filename.equals(filename);
        }
    }

    public static class Chained implements Ignore {
        private final Ignore car;
        private final Ignore cdr;
        public Chained(Ignore car, Ignore cdr) {
            this.car = car;
            this.cdr = cdr;
        }

        public boolean allow(String filename) {
            if(car.allow(filename)) {
                return cdr.allow(filename);
            } else {
                return false;
            }
        }
    }

    private String jsonOutputFile = "jescov.json.ser";
    private String xmlOutputFile = "coverage.xml";
    private String htmlOutputDir = "coverage-report";
    private boolean jsonOutputMerge = false;
    private Ignore ignore = new Allow();
    private boolean enabled = true;
    private String sourceDirectory = ".";

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }
    
    public String getXmlOutputFile() {
        return xmlOutputFile;
    }

    public void setJsonOutputFile(String file) {
        this.jsonOutputFile = file;
    }

    public void setXmlOutputFile(String file) {
        this.xmlOutputFile = file;
    }

    public void setHtmlOutputDir(String dir) {
        this.htmlOutputDir = dir;
    }

    public String getHtmlOutputDir() {
        return htmlOutputDir;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void ignore(String filename) {
        ignore(new File(filename));
    }

    public void ignore(Ignore ignore) {
        this.ignore = new Chained(ignore, this.ignore);
    }

    public String getJsonOutputFile() {
        return this.jsonOutputFile;
    }

    public boolean isJsonOutputMerge() {
        return this.jsonOutputMerge;
    }

    public void setJsonOutputMerge(boolean merge) {
        this.jsonOutputMerge = merge;
    }

    public boolean allow(String filename) {
        return ignore.allow(filename);
    }

    private Generator generator;

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public Generator getGenerator() {
        return this.generator;
    }
}
