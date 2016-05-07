package me.itzg.mcadmin;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * @author Geoff Bourne
 * @since 0.2
 */
public class GetVersionsTest {

    @Test
    public void testLocation() throws Exception {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(byteOut);

        final int found = GetVersions.extractVersions("https://mcadmin.net/", out);

        assertThat(byteOut.size(), not(is(0)));
        assertThat(found, not(is(0)));
    }
}