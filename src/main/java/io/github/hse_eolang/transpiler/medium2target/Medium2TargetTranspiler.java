package io.github.hse_eolang.transpiler.medium2target;

import io.github.hse_eolang.transpiler.mediumcodemodel.EOSourceFile;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOTargetFile;

import java.util.ArrayList;

public class Medium2TargetTranspiler {
    public static ArrayList<EOTargetFile> transpile(EOSourceFile file) {
        return file.transpile(null);
    }
}
