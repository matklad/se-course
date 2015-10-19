package commands;

import commander.Command;
import commander.CommanderService;
import commander.argparse.Arguments;

import java.io.IOException;

public final class GrepCommand implements Command {
    @Override
    public CommanderService.Result execute(final CommanderService service) {
        final Arguments args = service.argParser()
                .flag("i")
                .flag("w")
                .intKey("A")
                .positional("pattern")
                .positional("file")
                .parse();

        final boolean caseInsensitive = args.has("i");
        final boolean wholeWords = args.has("w");
        final int afterContext = args.getInt("A", 0);
        final String pattern = args.positional("pattern");
        final String file = args.positional("file");


        if (service.getArgs().size() != 1) {
            return service.failure("expected one argument, got " + service.getArgs().size());
        }

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
