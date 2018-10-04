package ca.umontreal.iro.parser;

import javafx.scene.control.Alert;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.File;

import static javafx.scene.control.Alert.AlertType;

public class AlertErrorListener extends BaseErrorListener {
    public static final AlertErrorListener INSTANCE = new AlertErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                            int column, String msg, RecognitionException e) {
        var fileName = new File(recognizer.getInputStream().getSourceName()).getName();
        new Alert(AlertType.ERROR, String.format("%s (line %d column %d): %s", fileName, line, column, msg)).show();
    }
}