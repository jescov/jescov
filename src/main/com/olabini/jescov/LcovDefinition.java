package com.olabini.jescov;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LcovDefinition {
    static final String SOURCE_NAME = "<<com.olabini.jescov.LcovDefinition.SOURCE>>";
    static final String SOURCE;
    static {
        String SOURCE_ = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(LcovDefinition.class.getResourceAsStream("/com/olabini/jescov/lcov_definition.js")));
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int read;
            while((read = br.read(buf, 0, 1024)) != -1) {
                sb.append(buf, 0, read);
            }
            SOURCE_ = sb.toString();
            br.close();
        } catch(Exception e) {}
        SOURCE = SOURCE_;
    }
}
