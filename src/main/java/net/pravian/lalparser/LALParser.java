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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a parser for the LAL format.
 *
 * @since 1.0
 */
public class LALParser implements List<Login> {

    /**
     * The Pattern for matching LAL lines.
     *
     * @see java.util.regex.Pattern
     */
    public static final Pattern LOGIN_PATTERN;

    private final List<Login> logins;

    static {
        final StringBuilder p = new StringBuilder();
        final String user = "[\\w@\\.\\-]+";
        final String complex = "[\\w@!@#$%^&*/(){}\\[<>,.?|\\.\\-\\]]+"; // Passwords, email

        // Demo: http://www.regexr.com/3ac4n
        //
        p.append("^"); // Start
        p.append("(\\/\\/.*)|"); // 1 Comment
        p.append("(?:"); // OR enclosing group

        //p.append("(\\u002e)?"); // 2 Invalid - Hacked out
        p.append("(" + user + ")").append(":"); // 2 Login
        p.append("(" + complex + ")"); // 3 Password
        p.append("(?: \\((" + complex + ")\\))?"); // 4 Display name
        p.append("(?: \\{(" + complex + ")\\})?"); // 5 Email
        p.append("(?: \\[(" + complex + ")\\])?"); // 6 Old password

        p.append(")"); // End OR enclosing group
        p.append("$"); // End

        LOGIN_PATTERN = Pattern.compile(p.toString());
    }

    /**
     * Creates a new empty LAL parser.
     */
    public LALParser() {
        logins = new ArrayList<>();
    }

    /**
     * Attempts to parse a file and stores the parsed body in this parser.
     *
     * @param file The file to parse.
     * @throws RuntimeException if the file could not be found.
     */
    public void load(File file) {
        Validate.notNull(file, "File may not be null");
        final FileInputStream stream;

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        load(stream);
    }

    /**
     * Attempts to parse an InputStream and stores the parsed body in this parser.
     *
     * @param stream The stream to parse.
     */
    public void load(InputStream stream) {
        Validate.notNull(stream, "Stream may not be null");

        load(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    /**
     * Attempts to parse a string and stores the parsed body in this parser.
     *
     * <b>Note</b>: The supplied string may be a multi-line LAL document.
     *
     * @param string The string to parse.
     */
    public void load(String string) {
        Validate.notNull(string, "String may not be null");

        load(new StringReader(string));
    }

    /**
     * Attempts to parse a Reader and stores the parsed body in this parser.
     *
     * @param reader The reader to parse.
     * @throws RuntimeException if the reader could not be read from.
     */
    public void load(Reader reader) {
        Validate.notNull(reader, "Reader may not be null");

        logins.clear();

        final BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);

        try {
            String line;

            while ((line = input.readLine()) != null) {

                final Login login = parse(line.trim());

                if (login != null) {
                    logins.add(login);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                input.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Writes the contents of this parser to an OutputStream.
     *
     * @param stream The OutputStream to write to.
     */
    public void write(OutputStream stream) {
        Validate.notNull(stream, "Stream may not be null");

        write(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
    }

    /**
     * Writes the contents of this parser to a Writer.
     *
     * @param writer The Writer to write to.
     */
    public void write(Writer writer) {
        Validate.notNull(writer, "Writer may not be null");

        if (isEmpty()) {
            return;
        }

        try {
            for (Login login : logins) {
                writer.write(compile(login));
                writer.write("\n");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (Exception ex) {
            }
        }

    }

    @Override
    public int size() {
        return logins.size();
    }

    @Override
    public boolean isEmpty() {
        return logins.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return logins.contains(o);
    }

    @Override
    public Object[] toArray() {
        return logins.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return logins.toArray(ts);
    }

    @Override
    public boolean add(Login e) {
        return logins.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return logins.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        return logins.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection<? extends Login> clctn) {
        return logins.addAll(clctn);
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        return logins.removeAll(clctn);
    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        return logins.removeAll(clctn);
    }

    @Override
    public Iterator<Login> iterator() {
        return logins.iterator();
    }

    @Override
    public void clear() {
        logins.clear();
    }

    @Override
    public boolean addAll(int i, Collection<? extends Login> clctn) {
        return logins.addAll(clctn);
    }

    @Override
    public Login get(int i) {
        return logins.get(i);
    }

    @Override
    public Login set(int i, Login e) {
        return logins.set(1, e);
    }

    @Override
    public void add(int i, Login e) {
        logins.add(i, e);
    }

    @Override
    public Login remove(int i) {
        return logins.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return logins.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return logins.lastIndexOf(o);
    }

    @Override
    public ListIterator<Login> listIterator() {
        return logins.listIterator();
    }

    @Override
    public ListIterator<Login> listIterator(int i) {
        return logins.listIterator(i);
    }

    @Override
    public List<Login> subList(int i, int i1) {
        return logins.subList(i, i1);
    }

    /**
     * Parses a String to a Login.
     *
     * @param line The line to parse.
     * @return The parsed login, or null if the line is not in the correct format.
     */
    public static Login parse(String line) {
        Validate.notEmpty(line, "Line may not be empty");

        // Start hack
        // TODO(JeromSar) Explain hack
        final boolean invalid = line.startsWith(".");
        if (invalid) {
            line = line.substring(1);
        }
        // End hack

        final Matcher matcher = LOGIN_PATTERN.matcher(line.trim());

        if (!matcher.find()) {
            return null;
        }

        final String comment = matcher.group(1);

        if (comment != null && !comment.isEmpty()) {
            return new Login(comment);
        }

        return new Login(
                matcher.group(2), // Login
                matcher.group(3), // Password
                matcher.group(4), // Display name
                matcher.group(5), // Email
                matcher.group(6), // Old password
                invalid);
    }

    /**
     * Compiles a Login to a String.
     *
     * @param login The login to parse.
     * @return The parsed login.
     * @throws IllegalArgumentException if the Login does not contain at least a password and username.
     */
    public static String compile(Login login) {
        Validate.notNull(login, "Login may not be null");

        if (login.isComment()) {
            return login.getComment();
        }

        final StringBuilder sb = new StringBuilder();

        Validate.notEmpty(login.getLogin(), "Login must contain at least a password and a username");
        Validate.noneEmpty(login.getPassword(), "Login must contain at least a password and a username");

        if (login.isInvalid()) {
            sb.append(".");
        }

        sb.append(login.getLogin()).append(":").append(login.getPassword());

        if (login.getDisplayName() != null) {
            sb.append(" (").append(login.getDisplayName()).append(")");
        }

        if (login.getEmail() != null) {
            sb.append(" {").append(login.getEmail()).append("}");
        }

        if (login.getOldPassword() != null) {
            sb.append(" [").append(login.getOldPassword()).append("]");
        }

        return sb.toString();
    }

}
