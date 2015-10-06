package commander;

import java.util.HashMap;
import java.util.Map;

public final class ShellBuilder {
    private final Map<String, Command> commands = new HashMap<>();
    @SuppressWarnings("FieldCanBeLocal")
    private final String prompt = ">>>";

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

    public Shell build() {
        return new Shell(prompt, commands);
    }
}
