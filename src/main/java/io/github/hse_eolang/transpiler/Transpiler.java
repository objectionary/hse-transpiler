package io.github.hse_eolang.transpiler;

import com.jcabi.log.Logger;
import io.github.hse_eolang.transpiler.xml2medium.Xml2MediumParser;
import io.github.hse_eolang.transpiler.medium2target.Medium2TargetTranspiler;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOSourceEntity;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOSourceFile;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOTargetFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public final class Transpiler {

    private File generatedDir;

    public Transpiler(File generatedDir) {
        this.generatedDir = generatedDir;
    }

    /**
     * Compile one XML file.
     *
     * @param path The path to the XML file being compiled.
     */
    public void compileHse(final Path path) {
        final File file = new File(path.toUri());
        final Xml2MediumParser xml = new Xml2MediumParser(file);
        try {
            final EOSourceEntity smth = xml.parse();
            final ArrayList<EOTargetFile> code =
                    Medium2TargetTranspiler.transpile(
                            (EOSourceFile) smth
                    );
            code.forEach(
                    javaFile -> {
                        try {
                            new Save(
                                    javaFile.getContents(),
                                    this.generatedDir.toPath().resolve(
                                            Paths.get(
                                                    javaFile.getFileName()
                                            )
                                    )
                            ).save();
                        } catch (final IOException exception) {
                            throw new IllegalStateException(
                                    String.format(
                                            "Can't read the path %s",
                                            path
                                    ),
                                    exception
                            );
                        }
                    }
            );
        } catch (final Xml2MediumParser.Xml2MediumParserException exception) {
            throw new IllegalStateException(
                    String.format(
                            "The HSE compiler failed to parse the %s file.",
                            file
                    ),
                    exception
            );
        }
        Logger.info(this, "%s compiled to %s with the HSE compiler", path, this.generatedDir);
    }
}
