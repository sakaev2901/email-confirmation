package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean checkPassword(String mail, String password) {
        UserDto userDto = usersRepository.findByMail(mail).orElse(new UserDto());
        if (userDto.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
