package commands;

import commander.CommanderService;
import commander.Command;

public final class WcCommand implements Command {
    @Override
    public CommanderService.Result execute(final CommanderService service) {
        int nLines = 0;
        int nWords = 0;
        int nChars = 0;
        for (final String line : service.readFileFromArgument()) {
            nLines += 1;
            nWords += line.split("\\s+").length;
            nChars += line.length();
        }
        service.out.printf("lines: %d\nwords: %d\nchars: %d\n%s\n",
                nLines, nWords, nChars, service.getArgs().get(0));
        return service.ok();
    }

    @Override
    public String man() {
        return "Usage: wc file\n" +
                "Reports the number of lines, words and symbols in the file";
    }
}
