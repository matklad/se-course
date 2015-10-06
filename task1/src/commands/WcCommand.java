package commands;

import commander.CommanderService;
import commander.ICommand;

public final class WcCommand implements ICommand {
    @Override
    public CommanderService.Result execute(final CommanderService s) {
        int nLines = 0;
        int nWords = 0;
        int nChars = 0;
        for (final String line : s.readFileFromArgument()) {
            nLines += 1;
            nWords += line.split("\\s+").length;
            nChars += line.length();
        }
        s.out.printf("lines: %d\nwords: %d\nchars: %d\n%s\n",
                nLines, nWords, nChars, s.getArgs().get(0));
        return s.ok();
    }

    @Override
    public String man() {
        return "Usage: wc file\n" +
                "Reports the number of lines, words and symbols in the file";
    }
}
