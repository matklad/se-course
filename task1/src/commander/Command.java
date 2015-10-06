package commander;

public interface Command {
    CommanderService.Result execute(CommanderService handler);

    String man();
}
