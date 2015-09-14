package commands;

import commander.CommanderService;
import commander.ICommand;

public final class CatCommand implements ICommand {
    @Override
    public CommanderService.Result execute(final CommanderService s) {
        s.readFileFromArgument().forEach(s.out::println);
        return s.ok();
    }

    @Override
    public String man() {
        return "Usage: cat file\n" +
                "prints the contents of the file";
    }
}
