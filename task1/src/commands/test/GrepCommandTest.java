package commands.test;

import commander.Shell;
import commander.ShellBuilder;
import commands.GrepCommand;
import org.junit.Assert;

import java.io.*;


public class GrepCommandTest {

    private String run(final String cmd) throws IOException {
        final BufferedReader in = new BufferedReader(new StringReader(cmd + "\nquit"));

        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(buf);
        final Shell shell = new ShellBuilder()
                .setInput(in)
                .setOutput(out)
                .add("grep", new GrepCommand())
                .build();
        shell.start();
        return buf.toString();
    }

    @org.junit.Test
    public void testBasic() throws Exception {
        final String result = run("grep \"Минимальный\" README.md");
        Assert.assertEquals("Минимальный синтаксис grep", result);
    }

    @org.junit.Test
    public void testRe1() throws Exception {
        final String result = run("grep \"Минимальный$\" README.md");
        Assert.assertEquals("", result);
    }

    @org.junit.Test
    public void testRe2() throws Exception {
        final String result = run("grep \"^Минимальный\" README.md");
        Assert.assertEquals("Минимальный синтаксис grep", result);
    }

    @org.junit.Test
    public void testCaseInsensitive() throws Exception {
        final String result = run("grep -i \"минимальный$\" README.md");
        Assert.assertEquals("Минимальный синтаксис grep", result);
    }

    @org.junit.Test
    public void testWords() throws Exception {
        final String result = run("grep -w \"Минимал\" README.md");
        Assert.assertEquals("", result);
    }

    @org.junit.Test
    public void testContext() throws Exception {
        final String result = run("grep -A 1 \"II\" README.md");
        Assert.assertEquals("Часть II (15.09.2015)\n" +
                "---------------------", result);
    }
}
