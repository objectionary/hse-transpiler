package io.github.hse_eolang.transpiler.medium2target;

import org.ainslec.picocog.PicoWriter;

/**
 *
 */
public class TranslationCommons {

    public static void bigComment(PicoWriter w, String... commentLines) {
        w.writeln("/**");
        for (String s : commentLines) {
            w.writeln(String.format(" * %s", s));
        }
        w.writeln(" */");
    }
}
