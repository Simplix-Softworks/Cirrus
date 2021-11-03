package dev.simplix.cirrus.common.i18n;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.val;

/**
 * This is a utility class for handy message replacements. Placeholders begin with { and are ending
 * with }
 * <br>
 * <b>Example:</b>
 * <br> {viewer}
 */
@Getter
@Accessors(fluent = true)
public final class Replacer {

    public static final String DELIMITER = "\n";
    private final List<String> messages;

    //Messages to replace
    private final List<String> variables = new ArrayList<>();
    private final List<Object> replacements = new ArrayList<>();

    private Replacer(List<String> messages) {
        // Cloning our messages
        this.messages = new ArrayList<>(messages);
    }

    // ----------------------------------------------------------------------------------------------------
    // Static Factory methods
    // ----------------------------------------------------------------------------------------------------
    public static Replacer of(@NonNull final String... messages) {
        return new Replacer(Arrays.asList(messages));
    }

    public static Replacer of(@NonNull final List<String> messages) {
        return new Replacer(messages);
    }

    public static Replacer ofMultilineString(@NonNull final String multilineString) {
        return new Replacer(Arrays.asList(multilineString.split(DELIMITER)));
    }

    public final Replacer find(@NonNull final String... variables) {
        this.variables.clear();
        this.variables.addAll(Arrays.asList(variables));
        return this;
    }

    public final Replacer replace(@NonNull final Object... replacements) {
        this.replacements.clear();

        this.replacements.addAll(Arrays.asList(replacements));
        return this;
    }

    public Replacer set(final int index, @NonNull final String message) {
        this.messages.set(index, message);
        return this;
    }

    /**
     * Replaces all variables with replacements.
     *
     * @param associativeArray Variable, Replacement, Variable, Replacement
     */
    public final Replacer replaceAll(@NonNull final Object... associativeArray) {
        this.variables.clear();
        this.replacements.clear();

        //Even: Value
        for (int i = 0; i < associativeArray.length; i++) {
            if (i % 2==0) {
                //Odd: Key
                val raw = associativeArray[i];
                Preconditions.checkState(
                        raw instanceof String,
                        "Expected String at " + raw + ", got " + raw.getClass()
                                .getSimpleName());
                this.variables.add((String) raw);
            } else {
                this.replacements.add(associativeArray[i]);
            }
        }
        return this;
    }

    /**
     * Method to replace the registered Replacers.
     */
    public String[] replacedMessage() {
        Preconditions.checkState(
                this.replacements.size()==this.variables.size(),
                "Variables " + this.variables.size()
                        + " != replacements " + this.replacements.size(),
                "Variables: " + this.variables.toString(),
                "Replacments: " + this.replacements
        );

        // Join and replace as 1 message for maximum performance
        String message = String.join(DELIMITER, this.messages);

        for (int i = 0; i < this.variables.size(); i++) {
            String found = this.variables.get(i);
            { // Auto insert brackets
                if (!found.startsWith("{")) {
                    found = "{" + found;
                }

                if (!found.endsWith("}")) {
                    found = found + "}";
                }
            }
            final Object rep = i <= this.replacements.size() ? this.replacements.get(i):null;

            message = message.replace(found, rep==null ? "":rep.toString());
        }

        return message.split(DELIMITER);
    }

    /**
     * Same as {@link #replacedMessage()} but joined using {@link String#join(CharSequence,
     * CharSequence...)} and the {@link #DELIMITER}
     */
    public String replacedMessageJoined() {
        return String.join(DELIMITER, replacedMessage());
    }
}
