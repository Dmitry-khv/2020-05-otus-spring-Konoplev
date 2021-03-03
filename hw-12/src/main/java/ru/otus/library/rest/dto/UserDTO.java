package ru.otus.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.library.domain.User;

@Document("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String login;
    private String password;
    private int accessLevel;

    public UserDTO(String login, String password, int accessLevel) {
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public User toDomain(UserDTO dto) {
        return new User(dto.getLogin(), dto.getPassword(), dto.getAccessLevel());
    }

    public UserDTO toDto(User user) {
        return new UserDTO(user.getLogin(), user.getPassword(), user.getAccessLevel());
    }
}
