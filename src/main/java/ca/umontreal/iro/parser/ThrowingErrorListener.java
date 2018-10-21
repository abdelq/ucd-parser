package ca.umontreal.iro.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import static java.lang.String.format;

class ThrowingErrorListener extends BaseErrorListener {
    /**
     * Provides a default instance of {@link ThrowingErrorListener}.
     */
    static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        throw new ParseCancellationException(format("line %d column %d: %s", line, charPositionInLine, msg));
    }
}
