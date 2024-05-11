package org.data.redroleplay.services;

public interface PasswordHasher {

    boolean verify(String hashedPassword, String salt , String password);

    String generateSalt();

    String generateHashedPassword(String password, String salt)
}
