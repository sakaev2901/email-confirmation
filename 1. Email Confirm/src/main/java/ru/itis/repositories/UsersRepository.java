package ru.itis.repositories;

import ru.itis.dto.UserDto;
import ru.itis.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByToken(String token);
    void updateConfirmation(User user);
    Optional<UserDto> findByMail(String mail);
}
