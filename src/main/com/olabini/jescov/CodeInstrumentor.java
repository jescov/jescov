package com.olabini.jescov;

import com.olabini.jescov.es3.ES3InstrumentLexer;
import com.olabini.jescov.es3.ES3InstrumentParser;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.CharArrayReader;

public class CodeInstrumentor {
    private final CoverageNameMapper mapper;

    public CodeInstrumentor(CoverageNameMapper mapper) {
      this.mapper = mapper;
    }

    private static final char[] TEMPLATE =
      ("group TestRewrite;\n" +
       "init_instrument(stmt, hash, name, lines) ::= \"LCOV_<hash>=" +
          "LCOV.initNoop(<name>,0,<lines>);BCOV_<hash>=BCOV.initNoop(<name>,<branches>);<stmt>\"" +
       "instrument(stmt, hash, ln) ::= \"LCOV_<hash>[<ln>]++; <stmt>\"" +
       "instrument_if(conditional, left, right, hash, bid) ::= \"if(((<conditional>) || BCOV_<hash>.branchFalseInc(<bid>)) && BCOV_<hash>.branchTrueInc(<bid>)) <left> <right>\"" +
       "instrument_qif(conditional, left, right, hash, bid) ::= \"(((<conditional>) || BCOV_<hash>.branchFalseInc(<bid>)) && BCOV_<hash>.branchTrueInc(<bid>)) ? <left> : <right>\"" +
       "instrument_while(conditional, stmt, hash, bid) ::= \"while(((<conditional>) || BCOV_<hash>.branchFalseInc(<bid>)) && BCOV_<hash>.branchTrueInc(<bid>)) <stmt>\"" +
       "instrument_for(pre, stmt, hash, bid) ::= \"BCOV_<hash>.branchFalseInc(<bid>);<pre> {BCOV_<hash>.branchTrueInc(<bid>); <stmt> }\"" +
       "instrument_and(left, right, hash, bid) ::= \"((BCOV_var_<hash>_<bid>=<left>) || BCOV_<hash>.branchFalseInc(<bid>) || BCOV_var_<hash>_<bid>) && BCOV_<hash>.branchTrueInc(<bid>) && <right>\"" +
       "instrument_or(left, right, hash, bid) ::= \"(((BCOV_var_<hash>_<bid>=<left>) && BCOV_<hash>.branchTrueInc(<bid>) && BCOV_var_<hash>_<bid>) || BCOV_<hash>.branchFalseInc(<bid>)) || <right>\"" +
       "instrument_case(expr, stmt, hash, bid, branchNum) ::= \"case <expr>: BCOV_<hash>.branchInc(<bid>, <branchNum>, true); <stmt>\"" +
       "instrument_default(stmt, hash, bid, branchNum) ::= \"default: BCOV_<hash>.branchInc(<bid>, <branchNum>, true); <stmt>\"" +
       "pass(stmt) ::= \"<stmt>\"").toCharArray();


    public String instrument(String file, String source) {
        StringTemplateGroup templates = new StringTemplateGroup(new CharArrayReader(TEMPLATE));
        ANTLRStringStream stream = new ANTLRStringStream(source);
        stream.name = String.valueOf(mapper.map(file));
        ES3InstrumentLexer lexer = new ES3InstrumentLexer(stream);
        TokenRewriteStream tokens = new TokenRewriteStream(lexer);
        ES3InstrumentParser parser = new ES3InstrumentParser(tokens);
        parser.setTemplateLib(templates);

        try {
            parser.program();
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }

        return tokens.toString();
    }
}
