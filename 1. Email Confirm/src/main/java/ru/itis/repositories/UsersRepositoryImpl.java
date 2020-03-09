package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

    private static final String SAVE = "INSERT INTO itis_user(name, email, password, token) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_TOKEN = "SELECT * FROM itis_user WHERE \"token\" = ?";
    private static final String UPDATE_CONFIRMATION = "UPDATE itis_user SET \"is_confirmed\" = ? WHERE \"id\" = ?";
    private static final String FIND_BY_MAIL = "SELECT (id, email, password) FROM itis_user WHERE \"email\" = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;


    private RowMapper<User> userRowMapper = (row, rowNum) ->
            User.builder()
                .name(row.getString("name"))
                .email(row.getString("email"))
                .token(row.getString("token"))
                .isConfirmed(row.getBoolean("is_confirmed"))
                .id(row.getLong("id"))
                .build();


    private RowMapper<UserDto> userDtoRowMapper = (row, rowNum) ->
            UserDto.builder()
                    .id(row.getLong("id"))
                    .mail(row.getString("email"))
                    .password(row.getString("password"))
                    .build();
    @Override
    public User findById(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User model) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE);
            statement.setString(1, model.getName());
            statement.setString(2, model.getEmail());
            statement.setString(3, model.getPassword());
            statement.setString(4, model.getToken());
            return statement;
        });
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<User> findByToken(String token) {
        User user = jdbcTemplate.queryForObject(FIND_BY_TOKEN, new Object[]{token}, userRowMapper);
        return Optional.ofNullable(user);
    }

    @Override
    public void updateConfirmation(User user) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_CONFIRMATION);
            statement.setBoolean(1, user.isConfirmed());
            statement.setLong(2, user.getId());
            return statement;
        });
    }

    @Override
    public Optional<UserDto> findByMail(String mail) {
        UserDto userDto = jdbcTemplate.queryForObject(FIND_BY_MAIL, new Object[]{mail}, userDtoRowMapper);
        return Optional.ofNullable(userDto);
    }
}
