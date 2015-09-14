import commander.Shell;
import commander.ShellBuilder;
import commands.CatCommand;
import commands.PwdCommand;
import commands.WcCommand;

final class Main {
    public static void main(final String[] _args) {
        final Shell c = new ShellBuilder()
                .add("cat", new CatCommand())
                .add("wc", new WcCommand())
                .add("pwd", new PwdCommand())
                .build();

        try {
            c.start();
        } catch (final Throwable e) {
            System.err.println("Unknown error occurred");
            System.exit(-1);
        }
    }
}
