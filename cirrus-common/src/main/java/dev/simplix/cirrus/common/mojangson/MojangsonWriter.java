package dev.simplix.cirrus.common.mojangson;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/**
 * A modified version of {@link JsonWriter} only using quotes when it is necessary. MojangsonWriter
 * is lenient by default.
 */
public class MojangsonWriter extends JsonWriter {

    /*
     * From RFC 7159, "All Unicode characters may be placed within the
     * quotation marks except for the characters that must be escaped:
     * quotation mark, reverse solidus, and the control characters
     * (U+0000 through U+001F)."
     *
     * We also escape '\u2028' and '\u2029', which JavaScript interprets as
     * newline characters. This prevents eval() from failing with a syntax
     * error. http://code.google.com/p/google-gson/issues/detail?id=341
     */
    private static final String[] REPLACEMENT_CHARS;
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;

    static {
        REPLACEMENT_CHARS = new String[128];
        for (int i = 0; i <= 0x1f; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", (int) i);
        }
        REPLACEMENT_CHARS['"'] = "\\\"";
        REPLACEMENT_CHARS['\\'] = "\\\\";
        REPLACEMENT_CHARS['\t'] = "\\t";
        REPLACEMENT_CHARS['\b'] = "\\b";
        REPLACEMENT_CHARS['\n'] = "\\n";
        REPLACEMENT_CHARS['\r'] = "\\r";
        REPLACEMENT_CHARS['\f'] = "\\f";
        HTML_SAFE_REPLACEMENT_CHARS = REPLACEMENT_CHARS.clone();
        HTML_SAFE_REPLACEMENT_CHARS['<'] = "\\u003c";
        HTML_SAFE_REPLACEMENT_CHARS['>'] = "\\u003e";
        HTML_SAFE_REPLACEMENT_CHARS['&'] = "\\u0026";
        HTML_SAFE_REPLACEMENT_CHARS['='] = "\\u003d";
        HTML_SAFE_REPLACEMENT_CHARS['\''] = "\\u0027";
    }

    /**
     * The output data, containing at most one top-level array or object.
     */
    private final Writer out;

    private int[] stack = new int[32];
    private int stackSize = 0;
    /**
     * A string containing a full set of spaces for a single level of indentation, or null for no
     * pretty printing.
     */
    private String indent;
    /**
     * The name/value separator; either ":" or ": ".
     */
    private String separator = ":";
    private boolean lenient;
    private boolean htmlSafe;
    private String deferredName;
    private boolean serializeNulls = true;

    {
        push(MojangsonScope.EMPTY_DOCUMENT);
    }

    /**
     * Creates a new instance that writes a MOJANGSON-encoded stream to {@code out}. For best
     * performance, ensure {@link Writer} is buffered; wrapping in {@link java.io.BufferedWriter
     * BufferedWriter} if necessary.
     */
    public MojangsonWriter(Writer out) {
        super(out);
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
        setLenient(true);
    }

    /**
     * Begins encoding a new array. Each call to this method must be paired with a call to {@link
     * #endArray}.
     *
     * @return this writer.
     */
    public MojangsonWriter beginArray() throws IOException {
        writeDeferredName();
        return open(MojangsonScope.EMPTY_ARRAY, '[');
    }

    /**
     * Ends encoding the current array.
     *
     * @return this writer.
     */
    public MojangsonWriter endArray() throws IOException {
        return close(MojangsonScope.EMPTY_ARRAY, MojangsonScope.NONEMPTY_ARRAY, ']');
    }

    /**
     * Begins encoding a new object. Each call to this method must be paired with a call to {@link
     * #endObject}.
     *
     * @return this writer.
     */
    public MojangsonWriter beginObject() throws IOException {
        writeDeferredName();
        return open(MojangsonScope.EMPTY_OBJECT, '{');
    }

    /**
     * Ends encoding the current object.
     *
     * @return this writer.
     */
    public MojangsonWriter endObject() throws IOException {
        return close(MojangsonScope.EMPTY_OBJECT, MojangsonScope.NONEMPTY_OBJECT, '}');
    }

    /**
     * Enters a new scope by appending any necessary whitespace and the given bracket.
     */
    private MojangsonWriter open(int empty, char openBracket) throws IOException {
        beforeValue();
        push(empty);
        out.write(openBracket);
        return this;
    }

    /**
     * Closes the current scope by appending any necessary whitespace and the given bracket.
     */
    private MojangsonWriter close(int empty, int nonempty, char closeBracket)
            throws IOException {
        int context = peek();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (deferredName != null) {
            throw new IllegalStateException("Dangling name: " + deferredName);
        }

        stackSize--;
        if (context == nonempty) {
            newline();
        }
        out.write(closeBracket);
        return this;
    }

    private void push(int newTop) {
        if (stackSize == stack.length) {
            stack = Arrays.copyOf(stack, stackSize * 2);
        }
        stack[stackSize++] = newTop;
    }

    /**
     * Returns the value on the top of the stack.
     */
    private int peek() {
        if (stackSize == 0) {
            throw new IllegalStateException("MojangsonWriter is closed.");
        }
        return stack[stackSize - 1];
    }

    /**
     * Replace the value on the top of the stack with the given value.
     */
    private void replaceTop(int topOfStack) {
        stack[stackSize - 1] = topOfStack;
    }

    /**
     * Encodes the property name.
     *
     * @param name the name of the forthcoming value. May not be null.
     * @return this writer.
     */
    public MojangsonWriter name(String name) throws IOException {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (deferredName != null) {
            throw new IllegalStateException();
        }
        if (stackSize == 0) {
            throw new IllegalStateException("MojangsonWriter is closed.");
        }
        deferredName = name;
        return this;
    }

    private void writeDeferredName() throws IOException {
        if (deferredName != null) {
            beforeName();
            string(deferredName);
            deferredName = null;
        }
    }

    /**
     * Encodes {@code value}.
     *
     * @param value the literal string value, or null to encode a null literal.
     * @return this writer.
     */
    public MojangsonWriter value(String value) throws IOException {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue();
        string(value);
        return this;
    }

    /**
     * Writes {@code value} directly to the writer without quoting or escaping.
     *
     * @param value the literal string value, or null to encode a null literal.
     * @return this writer.
     */
    public MojangsonWriter jsonValue(String value) throws IOException {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue();
        out.append(value);
        return this;
    }

    /**
     * Encodes {@code null}.
     *
     * @return this writer.
     */
    public MojangsonWriter nullValue() throws IOException {
        if (deferredName != null) {
            if (serializeNulls) {
                writeDeferredName();
            } else {
                deferredName = null;
                return this; // skip the name and the value
            }
        }
        beforeValue();
        out.write("null");
        return this;
    }

    /**
     * Encodes {@code value}.
     *
     * @return this writer.
     */
    public MojangsonWriter value(boolean value) throws IOException {
        writeDeferredName();
        beforeValue();
        out.write(value ? "true" : "false");
        return this;
    }

    /**
     * Encodes {@code value}.
     *
     * @return this writer.
     */
    public MojangsonWriter value(Boolean value) throws IOException {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue();
        out.write(value ? "true" : "false");
        return this;
    }

    /**
     * Encodes {@code value}.
     *
     * @param value a finite value. May not be {@link Double#isNaN() NaNs} or {@link
     *              Double#isInfinite() infinities}.
     * @return this writer.
     */
    public MojangsonWriter value(double value) throws IOException {
        writeDeferredName();
        if (!lenient && (Double.isNaN(value) || Double.isInfinite(value))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        beforeValue();
        out.append(Double.toString(value));
        return this;
    }

    /**
     * Encodes {@code value}.
     *
     * @return this writer.
     */
    public MojangsonWriter value(long value) throws IOException {
        writeDeferredName();
        beforeValue();
        out.write(Long.toString(value));
        return this;
    }

    /**
     * Encodes {@code value}.
     *
     * @param value a finite value. May not be {@link Double#isNaN() NaNs} or {@link
     *              Double#isInfinite() infinities}.
     * @return this writer.
     */
    public MojangsonWriter value(Number value) throws IOException {
        if (value == null) {
            return nullValue();
        }

        writeDeferredName();
        String string = value.toString();
        if (!lenient
                && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        beforeValue();
        out.append(string);
        return this;
    }

    /**
     * Ensures all buffered data is written to the underlying {@link Writer} and flushes that writer.
     */
    public void flush() throws IOException {
        if (stackSize == 0) {
            throw new IllegalStateException("MojangsonWriter is closed.");
        }
        out.flush();
    }

    /**
     * Flushes and closes this writer and the underlying {@link Writer}.
     *
     * @throws IOException if the JSON document is incomplete.
     */
    public void close() throws IOException {
        out.close();

        int size = stackSize;
        if (size > 1 || size == 1 && stack[size - 1] != MojangsonScope.NONEMPTY_DOCUMENT) {
            throw new IOException("Incomplete document");
        }
        stackSize = 0;
    }

    private void newline() throws IOException {
        if (indent == null) {
            return;
        }

        out.write('\n');
        for (int i = 1, size = stackSize; i < size; i++) {
            out.write(indent);
        }
    }

    /**
     * Inserts any necessary separators and whitespace before a name. Also adjusts the stack to expect
     * the name's value.
     */
    private void beforeName() throws IOException {
        int context = peek();
        if (context == MojangsonScope.NONEMPTY_OBJECT) { // first in object
            out.write(',');
        } else if (context != MojangsonScope.EMPTY_OBJECT) { // not in an object!
            throw new IllegalStateException("Nesting problem.");
        }
        newline();
        replaceTop(MojangsonScope.DANGLING_NAME);
    }

    /**
     * Inserts any necessary separators and whitespace before a literal value, inline array, or inline
     * object. Also adjusts the stack to expect either a closing bracket or another element.
     */
    @SuppressWarnings("fallthrough")
    private void beforeValue() throws IOException {
        switch (peek()) {
            case MojangsonScope.NONEMPTY_DOCUMENT:
                if (!lenient) {
                    throw new IllegalStateException(
                            "JSON must have only one top-level value.");
                }
                // fall-through
            case MojangsonScope.EMPTY_DOCUMENT: // first in document
                replaceTop(MojangsonScope.NONEMPTY_DOCUMENT);
                break;

            case MojangsonScope.EMPTY_ARRAY: // first in array
                replaceTop(MojangsonScope.NONEMPTY_ARRAY);
                newline();
                break;

            case MojangsonScope.NONEMPTY_ARRAY: // another in array
                out.append(',');
                newline();
                break;

            case MojangsonScope.DANGLING_NAME: // value for name
                out.append(separator);
                replaceTop(MojangsonScope.NONEMPTY_OBJECT);
                break;

            default:
                throw new IllegalStateException("Nesting problem.");
        }
    }

    private void string(String value) throws IOException {
        boolean quotesNeeded = value.contains(" ")
                || value.isEmpty();
        for (char c : value.toCharArray()) {
            if (c > 0x7a || (c > 0x39 && c < 0x41) || c < 0x30 || (c > 0x5a && c < 0x61)) {
                quotesNeeded = true;
                break;
            }
        }
        if (quotesNeeded) { // Mojangson uses lenient json
            out.write('\"');
        }
        int last = 0;
        int length = value.length();
        if (last < length) {
            out.write(value, last, length - last);
        }
        if (quotesNeeded) {
            out.write('\"');
        }
    }

}
