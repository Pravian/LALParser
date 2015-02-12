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

    protected Login(String comment) { // Protected to avoid ambiguity
        this.comment = comment;
    }

    public Login() {
        this(null, null);
    }

    public Login(String login, String password) {
        this(login, password, null);
    }

    public Login(String login, String password, String displayName) {
        this(login, password, displayName, null);
    }

    public Login(String login, String password, String displayName, String email) {
        this(login, password, displayName, email, null);
    }

    public Login(String login, String password, String displayName, String email, String oldPassword) {
        this(login, password, displayName, email, oldPassword, false);
    }

    public Login(String login, String password, String displayName, String email, String oldPassword, boolean invalid) {
        this(login, password, displayName, email, oldPassword, invalid, "");
    }

    public Login(String login, String password, String displayName, String email, String oldPassword, boolean invalid, String comment) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.oldPassword = oldPassword;
        this.invalid = invalid;
        this.comment = comment;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public String getComment() {
        return comment;
    }

    public boolean isComment() {
        return comment != null && !comment.isEmpty();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return LALParser.compile(this);
    }

    @Override
    public boolean equals(Object o) {
        return (o == null || !(o instanceof Login) ? false : o.hashCode() == hashCode());
    }

    public boolean strictEquals(Login login) {
        if (login == null) {
            return false;
        }

        if (comment != null && !comment.isEmpty()) { // Still unstrict equals for comments
            return comment.equals(login.getComment());
        }

        return this.login.equals(login.getLogin())
                && this.password.equals(login.getPassword())
                && this.displayName.equals(login.getDisplayName())
                && this.email.equals(login.getEmail())
                && this.oldPassword.equals(login.getOldPassword())
                && this.invalid == login.isInvalid();
    }

    @Override
    public int hashCode() {
        int hash = 7;

        if (comment != null && !comment.isEmpty()) {
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
