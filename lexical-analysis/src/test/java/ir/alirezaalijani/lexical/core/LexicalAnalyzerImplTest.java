package ir.alirezaalijani.lexical.core;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alireza Alijani : <a href="https://alirezaalijani.ir">https://alirezaalijani.ir</a>
 * @email alirezaalijani.ir@gmail.com
 * @date 12/28/2022
 */
class LexicalAnalyzerImplTest {

    @Test
    void analyze() throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("javasource/App.java");
        assertNotNull(resource);
        String allLines = Files.readString(Path.of(resource.toURI()));

        LexicalAnalyzer analyzer=new LexicalAnalyzerImpl(allLines);
        var lexicalList = analyzer.analyze();

        lexicalList.forEach(System.out::println);
    }
}