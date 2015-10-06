package commands;

import commander.CommanderService;
import commander.ICommand;

import java.io.IOException;

public final class GrepCommand implements ICommand {
    @Override
    public CommanderService.Result execute(final CommanderService handler) {
        if (handler.getArgs().size() != 1) {
            return handler.failure("expected one argument, got " + handler.getArgs().size());
        }

        final String pattern = handler.getArgs().get(0);
        try {
            while (true) {
                final String line = handler.in.readLine();
                if (line == null) {
                    break;
                }
                if (line.contains(pattern)) {
                    handler.out.println(line);
                }

            }
        } catch (final IOException ignored) {
            return handler.failure("grep: IO failure");
        }

        return handler.ok();
    }

    @Override
    public String man() {
        return "Usage: grep foo\n" +
                "search for pattern in input";
    }
}
