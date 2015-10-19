package commands.test;

import commander.Shell;
import commander.ShellBuilder;
import commands.GrepCommand;
import junit.framework.Assert;

import java.io.*;


public class GrepCommandTest {

    private void checkOutput(final String cmd, final String expectedStdout) throws IOException {
        final BufferedReader in = new BufferedReader(new StringReader(cmd + "\nquit"));

        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(buf);
        final Shell shell = new ShellBuilder()
                .setInput(in)
                .setOutput(out)
                .add("grep", new GrepCommand())
                .build();
        shell.start();
        Assert.assertEquals(buf.toString().trim(), expectedStdout);
    }

    @org.junit.Test
    public void testBasic() throws Exception {
        checkOutput("grep \"Минимальный\" README.md",
                "Минимальный синтаксис grep");

    }

    @org.junit.Test
    public void testRe1() throws Exception {
        checkOutput("grep \"Минимальный$\" README.md",
                "");
    }

    @org.junit.Test
    public void testRe2() throws Exception {
        checkOutput("grep \"^Минимальный\" README.md",
                "Минимальный синтаксис grep");
    }

    @org.junit.Test
    public void testCaseInsensitive() throws Exception {
        checkOutput("grep -i \"минимальный$\" README.md",
                "Минимальный синтаксис grep");
    }

    @org.junit.Test
    public void testWords() throws Exception {
        checkOutput("grep -w \"Минимал\" README.md",
                "");
    }

    @org.junit.Test
    public void testContext() throws Exception {
        checkOutput("grep -A 1 \"II\" README.md",
                "Часть II (15.09.2015)\n" +
                        "---------------------");

    }


}