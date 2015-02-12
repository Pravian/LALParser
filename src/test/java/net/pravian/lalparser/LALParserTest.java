package net.pravian.lalparser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LALParserTest {

    private final String loginString;
    private Login login;
    private Login invalidLogin;

    public LALParserTest() {
        loginString = ".user:pass (display) {email} [oldpass]";
    }

    @Before
    public void setUp() {
        login = new Login("user", "pass", "display", "email", "oldpass", true);
        invalidLogin = new Login("", "");
    }

    @After
    public void tearDown() {
    }

    @Test(expected = Exception.class)
    public void testInvalidCompile() {
        LALParser.compile(invalidLogin);
    }

    @Test
    public void testCompile() {
        Assert.assertTrue(LALParser.compile(login) != null);
    }

    @Test
    public void testParse() {
        Assert.assertTrue(LALParser.parse(loginString) != null);
    }

    @Test
    public void testCompileAssertion() {
        Assert.assertTrue(LALParser.compile(login).equals(loginString));
    }

    @Test
    public void testParseAssertion() {
        Assert.assertTrue(LALParser.parse(loginString).equals(login));
        Assert.assertTrue(LALParser.parse(loginString).strictEquals(login));
    }

    @Test
    public void testTwoWayCompile() {
        Assert.assertTrue(LALParser.compile(LALParser.parse(loginString)).equals(loginString));
    }

    @Test
    public void testTwoWayParse() {
        Assert.assertTrue(LALParser.parse(LALParser.compile(login)).equals(login));
        Assert.assertTrue(LALParser.parse(LALParser.compile(login)).strictEquals(login));
    }

}
