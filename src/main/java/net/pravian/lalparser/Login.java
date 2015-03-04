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

import java.util.Objects;

/**
 * Represents a Login in the LAL format or a comment.
 *
 * @since 1.0
 */
public class Login {

    private String comment = null;
    private String login = null;
    private String password = null;
    private String displayName = null;
    private String email = null;
    private String oldPassword = null;
    private boolean invalid = false;

    /**
     * Creates a new Login instance with empty credentials.
     *
     * <b>Note</b>: LAL logins must contain at least a login and password to be compiled.
     */
    public Login() {
        this(null, null);
    }

    /**
     * Creates a new Login instance with the specified comment.
     *
     * @param comment The comment to use.
     */
    public Login(String comment) {
        this.comment = comment;
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     */
    public Login(String login, String password) {
        this(login, password, null);
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     * @param displayName The display name to use.
     */
    public Login(String login, String password, String displayName) {
        this(login, password, displayName, null);
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     * @param displayName The display name to use.
     * @param email The email to use.
     */
    public Login(String login, String password, String displayName, String email) {
        this(login, password, displayName, email, null);
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     * @param displayName The display name to use.
     * @param email The email to use.
     * @param oldPassword The old password to use.
     */
    public Login(String login, String password, String displayName, String email, String oldPassword) {
        this(login, password, displayName, email, oldPassword, false);
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     * @param displayName The display name to use.
     * @param email The email to use.
     * @param oldPassword The old password to use.
     * @param invalid If this Login is invalid or not.
     */
    public Login(String login, String password, String displayName, String email, String oldPassword, boolean invalid) {
        this(login, password, displayName, email, oldPassword, invalid, "");
    }

    /**
     * Creates a new Login instance.
     *
     * @param login The login to use.
     * @param password The password to use.
     * @param displayName The display name to use.
     * @param email The email to use.
     * @param oldPassword The old password to use.
     * @param invalid If this Login is invalid or not.
     * @param comment The comment to use.
     */
    public Login(String login, String password, String displayName, String email, String oldPassword, boolean invalid, String comment) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.oldPassword = oldPassword;
        this.invalid = invalid;
        this.comment = comment;
    }

    /**
     * Returns the login detail in this Login.
     *
     * @return The login detail.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the password detail in this Login.
     *
     * @return The password detail.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the display name detail in this Login.
     *
     * @return The display name detail.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the email detail in this Login.
     *
     * @return The email detail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the old password detail in this Login.
     *
     * @return The old password detail.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Returns true if this Login is invalid.
     *
     * @return True if this login is invalid.
     */
    public boolean isInvalid() {
        return invalid;
    }

    /**
     * Returns the comment in this Login.
     *
     * @return The comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns true if this Login is a comment.
     *
     * @return True if this login is a comment.
     */
    public boolean isComment() {
        return comment != null && !comment.isEmpty();
    }

    /**
     * Sets the login detail in this Login.
     *
     * @param login The login detail.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets the password detail in this Login.
     *
     * @param password The password detail.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the display name detail in this Login.
     *
     * @param displayName The display name detail.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the email detail in this Login.
     *
     * @param email The email detail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the old password detail in this Login.
     *
     * @param oldPassword The old password detail.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Sets if this Login is invalid or not.
     *
     * @param invalid the invalidness of this Login.
     */
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    /**
     * Sets the comment in this Login.
     *
     * @param comment The comment in this Login.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Compiles this login.
     *
     * @return The compiled login.
     * @see LALParser#compile(net.pravian.lalparser.Login)
     */
    @Override
    public String toString() {
        return LALParser.compile(this);
    }

    @Override
    /**
     * Verifies equality with another Login.
     *
     * @return True if the supplied object is equal to this object.
     * @see Login#strictEquals(net.pravian.lalparser.Login)
     */
    public boolean equals(Object o) {
        return (o == null || !(o instanceof Login) ? false : o.hashCode() == hashCode());
    }

    /**
     * Verifies strict equality with another Login.
     *
     * Strict equality works as following:
     * If the login is a comment, its equality is tested with the supplied parameter, the result is returned.
     * Otherwise, this method only returns true if the validness, login, password, displayName, email and oldPassword parameters are equal to each other.
     *
     * @param login
     * @return True if the supplied object is strictly equal to this object.
     */
    public boolean strictEquals(Login login) {
        if (login == null) {
            return false;
        }

        if (isComment()) { // Still unstrict equals for comments
            return login.isComment() && login.isComment() && comment.equals(login.getComment());
        }

        return Objects.equals(this.login, login.login)
                && Objects.equals(this.password, login.password)
                && Objects.equals(this.displayName, login.displayName)
                && Objects.equals(this.email, login.email)
                && Objects.equals(this.oldPassword, login.oldPassword)
                && this.invalid == login.isInvalid();
    }

    @Override
    public int hashCode() {
        int hash = 7;

        if (isComment()) {
            hash = 11 * hash + Objects.hashCode(this.comment);
            return hash;
        }

        // Equality based on validness, login, password
        hash = 11 * hash + Objects.hashCode(this.login);
        hash = 11 * hash + Objects.hashCode(this.password);
        hash = 11 * hash + (this.invalid ? 1 : 0);
        return hash;
    }

}
