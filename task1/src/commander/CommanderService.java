package commander;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class CommanderService {
    final private List<String> args;
    final public PrintStream out;

    CommanderService(final List<String> args, final PrintStream out) {
        if (args.size() == 0) {
            throw new IllegalArgumentException("args should include at least the name of command");
        }
        this.args = args;
        this.out = out;
    }

    public List<String> getArgs() {
        return args.subList(1, args.size());
    }

    public Result ok() {
        return new Result(null);
    }

    public Result failure(final String message) {
        if (message == null) {
            throw new IllegalArgumentException("message should not be null");
        }
        return new Result(message);
    }

    public List<String> readFileFromArgument() {
        if (getArgs().size() != 1) {
            throw new CommandException("expected one argument");
        }
        final Path path = Paths.get(getArgs().get(0));
        final List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (final IOException e) {
            throw  new CommandException("can't read the file " + path);
        }
        return lines;
    }

    public class CommandException extends RuntimeException {
        final String message;


        public CommandException(final String message) {
            this.message = message;
        }
    }

    public class Result {
        private final String error;

        Result(final String error) {
            this.error = error;
        }

        public boolean isOk() {
            return error == null;
        }

        public String getError() {
            return error;
        }
    }
}
