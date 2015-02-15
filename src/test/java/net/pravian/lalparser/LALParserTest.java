/*
 * Copyright 2015 Jerom van der Sar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
