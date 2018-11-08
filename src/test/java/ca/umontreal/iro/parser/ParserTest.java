package ca.umontreal.iro.parser;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private static Parser parser;

    @BeforeAll
    static void init() {
        parser = new Parser();
    }

    @Test
    void validFile() {
        assertDoesNotThrow(() -> parser.parseModel(new File("src/test/resources/Ligue.ucd")));
    }

    @Test
    void emptyFile() {
        assertThrows(ParseCancellationException.class, () -> parser.parseModel(""));
    }
}
