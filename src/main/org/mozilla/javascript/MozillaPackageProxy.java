package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

public class MozillaPackageProxy {
    public static void copyInterpreterInternals(Script from, DebuggableScript to) {
        InterpreterData fromId = ((InterpretedFunction)from).idata;
        InterpreterData toId = (InterpreterData)to;

        toId.itsName = fromId.itsName;
        toId.itsSourceFile = fromId.itsSourceFile;
        toId.itsNeedsActivation = fromId.itsNeedsActivation;
        toId.itsFunctionType = fromId.itsFunctionType;

        toId.itsStringTable = fromId.itsStringTable;
        toId.itsDoubleTable = fromId.itsDoubleTable;
        toId.itsNestedFunctions = fromId.itsNestedFunctions;
        toId.itsRegExpLiterals = fromId.itsRegExpLiterals;

        toId.itsICode = fromId.itsICode;

        toId.itsExceptionTable = fromId.itsExceptionTable;

        toId.itsMaxVars = fromId.itsMaxVars;
        toId.itsMaxLocals = fromId.itsMaxLocals;
        toId.itsMaxStack = fromId.itsMaxStack;
        toId.itsMaxFrameArray = fromId.itsMaxFrameArray;

        toId.argNames = fromId.argNames;
        toId.argIsConst = fromId.argIsConst;
        toId.argCount = fromId.argCount;

        toId.itsMaxCalleeArgs= fromId.itsMaxCalleeArgs;

        toId.encodedSource = fromId.encodedSource;
        toId.encodedSourceStart= fromId.encodedSourceStart;
        toId.encodedSourceEnd = fromId.encodedSourceEnd;

        toId.languageVersion = fromId.languageVersion;

        toId.useDynamicScope = fromId.useDynamicScope;
        toId.isStrict = fromId.isStrict;
        toId.topLevel = fromId.topLevel;

        toId.literalIds = fromId.literalIds;

        toId.longJumps = fromId.longJumps;

        toId.firstLinePC = fromId.firstLinePC;

        toId.parentData = fromId.parentData;

        toId.evalScriptFlag = fromId.evalScriptFlag;
    }
}
