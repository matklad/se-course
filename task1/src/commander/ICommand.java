package commander;

public interface ICommand {
    CommanderService.Result execute(CommanderService handler);

    String man();
}
