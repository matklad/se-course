package commands;

import commander.CommanderService;
import commander.Command;

import java.io.IOException;

public final class GrepCommand implements Command {
    @Override
    public CommanderService.Result execute(final CommanderService service) {
        if (service.getArgs().size() != 1) {
            return service.failure("expected one argument, got " + service.getArgs().size());
        }

        final String pattern = service.getArgs().get(0);
        try {
            while (true) {
                final String line = service.in.readLine();
                if (line == null) {
                    break;
                }
                if (line.contains(pattern)) {
                    service.out.println(line);
                }

            }
        } catch (final IOException ignored) {
            return service.failure("grep: IO failure");
        }

        return service.ok();
    }

    @Override
    public String man() {
        return "Usage: grep foo\n" +
                "search for pattern in input";
    }
}
