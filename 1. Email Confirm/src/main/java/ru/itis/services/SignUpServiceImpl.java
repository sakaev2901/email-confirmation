package ru.itis.services;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
import ru.itis.config.Sender;
import ru.itis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.repositories.UsersRepository;

import javax.mail.search.SearchTerm;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Sender sender;

    private User user;
    private String message = "";

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
        readMessageFile();
        sender.send("Test", message,"sakaev2901@gmail.com", user.getEmail());
    }

    private void readMessageFile() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        FileTemplateLoader fileTemplateLoader = null;
        try {
            fileTemplateLoader = new FileTemplateLoader(new File("C:\\Projects\\email-confirmation\\1. Email Confirm\\src\\main\\webapp\\WEB-INF\\templates"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        configuration.setTemplateLoader(fileTemplateLoader);
        Map<String, Object> map = new HashMap<>();
        map.put("token", user.getToken());
        try {
            Template template = configuration.getTemplate("message.ftl");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Writer writer = new OutputStreamWriter(outputStream);
            template.process(map, writer);
            byte[] array = outputStream.toByteArray();

            for (byte ch:
                    array) {
                message += (char)ch;
            }
            System.out.println(message);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }
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
