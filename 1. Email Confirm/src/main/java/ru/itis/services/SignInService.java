package ru.itis.services;

public interface SignInService {
    boolean checkPassword(String mail, String password);
}
