package commands;

import commander.CommanderService;
import commander.Command;

public final class CatCommand implements Command {
    @Override
    public CommanderService.Result execute(final CommanderService service) {
        service.readFileFromArgument().forEach(service.out::println);
        return service.ok();
    }

    @Override
    public String man() {
        return "Usage: cat file\n" +
                "prints the contents of the file";
    }
}
