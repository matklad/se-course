package commander;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public final class ShellBuilder {
    private final Map<String, Command> commands = new HashMap<>();
    @SuppressWarnings("FieldCanBeLocal")
    private final String prompt = ">>>";
    private PrintStream out = System.out;
    private BufferedReader in;


    public ShellBuilder() {

    }

    public ShellBuilder add(final String name, final Command cmd) {
        if (commands.containsKey(name) ||
                Shell.exitCommand.equals(name) ||
                Shell.manCommand.equals(name)) {
            throw new IllegalArgumentException("Duplicate command " + name);
        }
        final String man = cmd.man();
        if (man == null || "".equals(man)) {
            throw new IllegalArgumentException("Please provide a man for " + name);
        }
        commands.put(name, cmd);
        return this;
    }

    public ShellBuilder setOutput(final PrintStream out) {
        this.out = out;
        return this;
    }

    public ShellBuilder setInput(final BufferedReader in) {
        this.in = in;
        return this;
    }

    public Shell build() {
        if (in == null) {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        return new Shell(prompt, commands, out, in);
    }
}
