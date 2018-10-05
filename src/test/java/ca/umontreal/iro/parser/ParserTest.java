package ca.umontreal.iro.parser;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ParserTest {
    @Test
    void parseLigue() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> {
            parser.parseModel(new File("src/test/resources/Ligue.ucd"));
        });
    }
}
