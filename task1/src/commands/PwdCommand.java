package commands;

import commander.CommanderService;
import commander.ICommand;

public final class PwdCommand implements ICommand {
    @Override
    public CommanderService.Result execute(final CommanderService s) {
        if (s.getArgs().size() != 0) {
            return s.failure("Expected no arguments");
        }
        System.out.println(System.getProperty("user.dir"));
        return s.ok();
    }

    @Override
    public String man() {
        return "Usage: pwd\nPrints current working directory";
    }
}
