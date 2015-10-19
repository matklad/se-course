package commander.argparse;

public class Arguments {

    public boolean has(final String i) {
        return false;
    }

    public int getInt(final String key, final int defaultValue) {
        return defaultValue;
    }

    public String positional(final String i) {
        return null;
    }
}
