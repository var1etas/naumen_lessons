package ru.naumen.collection.task2;

import java.util.Arrays;
import java.util.Objects;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    public User(String username, String email, byte[] passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public User() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.deepEquals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, Arrays.hashCode(passwordHash));
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash=" + Arrays.toString(passwordHash) +
                '}';
    }
}
