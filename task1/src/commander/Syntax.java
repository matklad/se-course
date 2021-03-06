package commander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Syntax {
    static List<CmdInvocation> parse(final String line) {
        final List<CmdInvocation> result = new ArrayList<>();
        for (String part: line.split("\\|")) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }
            final String[] args = part.split("\\s+");
            if (args.length == 0) {
                continue;
            }
            final CmdInvocation cmd = new CmdInvocation(args[0], Arrays.asList(args));
            result.add(cmd);
        }

        return result;
    }

    static class CmdInvocation {
        final String cmd;

        final List<String> args;
        private CmdInvocation(final String cmd, final List<String> args) {
            this.cmd = cmd;
            this.args = args;
        }

    }
}
