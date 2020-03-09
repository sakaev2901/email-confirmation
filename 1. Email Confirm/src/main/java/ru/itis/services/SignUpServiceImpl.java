package ru.itis.services;

import org.apache.commons.lang3.RandomStringUtils;
import ru.itis.config.Sender;
import ru.itis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.repositories.UsersRepository;

import javax.mail.search.SearchTerm;
import java.util.Map;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Sender sender;

    private User user;
    private String message =
            "    <a style=\"background-color: rebeccapurple\" href=\"http://localhost:8080/activate/\">\n" +
            "        Подтвердить\n" +
            "    </a>\n";

    @Override
    public void signUp(Map<String, String> params) {
        user = User.builder()
                .name(params.get("name"))
                .email(params.get("email"))
                .password(params.get("password"))
                .token(createToken())
                .isConfirmed(false)
                .build();
        sendConfirmation();
        usersRepository.save(user);
    }

    @Override
    public void sendConfirmation() {
        sender.send("Test", "    <a style=\"background-color: rebeccapurple\" href=\"http://localhost:8080/activate/\">\n" +user.getToken()+
                "        Подтвердить\n" +
                "    </a>\n" , "sakaev2901@gmail.com", user.getEmail());
    }


    private String createToken() {
        return RandomStringUtils.random(20, true, true);
    }

    @Override
    public void checkConfirmation(String token) {
        user = usersRepository.findByToken(token).get();
        if (user.getToken().equals(token)) {
            user.setConfirmed(true);
            usersRepository.updateConfirmation(user);
        }
    }


}
