package ru.itis.services;

import java.util.Map;

public interface SignUpService {
    void signUp(Map<String, String> params);
    void sendConfirmation();
    void checkConfirmation(String token);
}
