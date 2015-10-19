package commander.argparse;


import java.util.List;

public class ArgParser {
    public ArgParser(final List<String> args) {

    }

    public ArgParser flag(final String i) {
        return this;
    }

    public ArgParser intKey(final String key) {
        return this;
    }

    public ArgParser positional(final String pattern) {
        return this;
    }

    public Arguments parse() {
        return new Arguments();
    }
}
