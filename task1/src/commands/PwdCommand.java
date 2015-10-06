package commands;

import commander.CommanderService;
import commander.Command;

public final class PwdCommand implements Command {
    @Override
    public CommanderService.Result execute(final CommanderService service) {
        if (service.getArgs().size() != 0) {
            return service.failure("Expected no arguments");
        }
        service.out.println(System.getProperty("user.dir"));
        return service.ok();
    }

    @Override
    public String man() {
        return "Usage: pwd\nPrints current working directory";
    }
}
