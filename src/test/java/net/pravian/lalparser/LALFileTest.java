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

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LALFileTest {

    private LALParser parser;
    private Login simpleLogin;
    private File simpleFile;
    private File complexFile;
    private final Login[] complexLogins = new Login[]{
        new Login("// Comment"),
        new Login("user", "pass"),
        new Login("user", "pass", "display"),
        new Login("user", "pass", "display", "email", null, true),
        new Login("user", "pass", "display", "email", "oldpass", false),
        new Login("user", "pass", "display", "email", "oldpass", true),};

    @Before
    public void setUp() {
        parser = new LALParser();
        simpleLogin = new Login("user", "pass", "display", "email", "oldpass", true);
        simpleFile = getResource("simple.txt");
        complexFile = getResource("complex.txt");

        if (!simpleFile.exists() || !complexFile.exists()) {
            Assert.fail();
        }
    }

    @Test(expected = Exception.class)
    public void testNull() {
        parser.load((String) null);
    }

    @Test
    public void testFileParseSimple() {
        parser.clear();
        parser.load(simpleFile);

        Assert.assertTrue(parser.size() == 1);
        Assert.assertTrue(parser.contains(simpleLogin));
        Assert.assertTrue(parser.get(0).strictEquals(simpleLogin));
    }

    @Test
    public void testFileParseComplex() {
        parser.clear();
        parser.load(complexFile);

        Assert.assertTrue(parser.size() == complexLogins.length);
        for (int i = 0; i < parser.size(); i++) {
            Assert.assertTrue("Testing: " + parser.get(i).toString(), parser.get(i).strictEquals(complexLogins[i]));
        }
    }

    private File getResource(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }

}
