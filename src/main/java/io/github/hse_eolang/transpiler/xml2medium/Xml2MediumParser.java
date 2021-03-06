package io.github.hse_eolang.transpiler.xml2medium;

import io.github.hse_eolang.transpiler.mediumcodemodel.EOAbstraction;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOSourceEntity;
import io.github.hse_eolang.transpiler.mediumcodemodel.EOSourceFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Xml2MediumParser {

    private final File file;
    private XPath xPath;
    private Document doc;

    public Xml2MediumParser(File file) {
        this.file = file;
    }

    public EOSourceEntity parse() throws Xml2MediumParserException {
        loadXmlDocument();

        EOSourceFile sourceFile = FileMetadataParsingUtils.parseSourceFile(this.file, this.doc, this.xPath);

        ArrayList<EOAbstraction> objects = ObjectsParsingUtils.parseObjects(this.file, this.doc, this.xPath, sourceFile);
        sourceFile.addObjects(objects.toArray(new EOAbstraction[objects.size()]));
        return sourceFile;
    }

    private void loadXmlDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            this.doc = dBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.xPath = XPathFactory.newInstance().newXPath();
    }

    public static class Xml2MediumParserException extends Exception {

        public Xml2MediumParserException(String message) {
            super(message);
        }
    }
}
