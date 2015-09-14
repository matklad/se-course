package commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

public final class Shell {
    static final String exitCommand = "quit";
    static final String manCommand = "man";
    private final Map<String, ICommand> commands;
    private final String prompt;
    private final PrintStream out = System.out;
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Shell(final String prompt, final Map<String, ICommand> commands) {
        this.commands = new HashMap<>(commands);
        this.prompt = prompt;
    }

    public void start() throws IOException {
        while (true) {
            displayPrompt();
            final Input input = readInput();
            if (exitCommand.equals(input.cmd)) {
                return;
            }

            if (manCommand.equals(input.cmd)) {
                final String target = input.args.get(1);
                final ICommand cmd = findCommand(target);
                if (cmd == null) {
                    displayError("Unknown command " + target);
                } else {
                    out.println(cmd.man());
                }

                continue;
            }

            final ICommand cmd = findCommand(input.cmd);
            if (cmd == null) {
                displayError("Invalid command " + input.cmd);

            } else {
                final CommanderService handler = new CommanderService(input.args, out);
                try {
                    final CommanderService.Result result = cmd.execute(handler);
                    if (!result.isOk()) {
                        displayError(result.getError());
                    }
                } catch (final CommanderService.CommandException e) {
                    displayError(e.message);
                }

            }

        }
    }

    private Input readInput() throws IOException {
        final String[] parts = in.readLine().trim().split("\\s+");
        if (parts.length == 0) {
            return new Input(null, new ArrayList<>());
        }
        return new Input(parts[0], Arrays.asList(parts));
    }

    private void displayPrompt() {
        display(prompt + " ");
    }

    private void displayError(final String message) {
        display("Error! " + message + "\n");
    }

    private void display(final String message) {
        out.print(message);
    }

    private ICommand findCommand(final String name) {
        return commands.get(name);
    }

    private class Input {
        final String cmd;
        final List<String> args;

        public Input(final String cmd, final List<String> args) {
            this.cmd = cmd;
            this.args = args;
        }
    }
}
