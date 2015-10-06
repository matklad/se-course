package commander;

import java.io.*;
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
        loop: while (true) {
            displayPrompt();
            final List<Syntax.CmdInvocation> cmdInvocations = readInput();
            if (cmdInvocations.size() == 0) {
                continue;
            }
            final Syntax.CmdInvocation first = cmdInvocations.get(0);
            if (exitCommand.equals(first.cmd)) {
                return;
            }
            if (manCommand.equals(first.cmd)) {
                final String target = first.args.get(0);
                final ICommand cmd = findCommand(target);
                if (cmd == null) {
                    displayError("Unknown command " + target);
                } else {
                    out.println(cmd.man());
                }

                continue;
            }

            final List<ResolvedInvocation> pipeline = new ArrayList<>();
            for (final Syntax.CmdInvocation cmdInvocation : cmdInvocations) {
                final ResolvedInvocation resolved = resolve(cmdInvocation);
                if (resolved == null) {
                    displayError("Unknown command " + cmdInvocation.cmd);
                    continue loop;
                }
                pipeline.add(resolved);
            }

            execPipeline(pipeline);
        }
    }

    private void execPipeline(final List<ResolvedInvocation> pipeline) {
        // This is a suboptimal solution, because it buffers all intermediate output in memory.
        // However, spawning a thread per pipeline element is also a bad solution: it is a bit complicated and
        // requires the user of the library to make her code thread safe.

        // The nice solution would be a generator-based pipeline (see Python Cookbook, 4.13, Creating Data Processing Pipelines).
        // Alas, there are no generators in Java :(
        byte[] buf = new byte[0];
        int idx = 0;
        for (final ResolvedInvocation part : pipeline) {
            final boolean isLast = idx == pipeline.size() - 1;
            final ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
            final ByteArrayInputStream inBuf = new ByteArrayInputStream(buf);

            final CommanderService handler = new CommanderService(part.args,
                    new BufferedReader(new InputStreamReader(inBuf)),
                    isLast ? out: new PrintStream(outBuf)
            );

            try {
                final CommanderService.Result result = part.cmd.execute(handler);
                if (!result.isOk()) {
                    displayError(result.getError());
                    break;
                }
            } catch (final CommanderService.CommandException e) {
                displayError(e.message);
                break;
            }

            buf = outBuf.toByteArray();
            idx += 1;
        }

    }

    private List<Syntax.CmdInvocation> readInput() throws IOException {
        return Syntax.parse(in.readLine());
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

    private ResolvedInvocation resolve(final Syntax.CmdInvocation cmd) {
        final ICommand c = findCommand(cmd.cmd);
        if (c == null) {
            return null;
        }
        return new ResolvedInvocation(c, cmd.args);
    }

    private ICommand findCommand(final String cmd) {
        return commands.get(cmd);
    }

    private final class ResolvedInvocation {
        final ICommand cmd;
        final List<String> args;

        private ResolvedInvocation(final ICommand cmd, final List<String> args) {
            this.cmd = cmd;
            this.args = args;
        }
    }
}
