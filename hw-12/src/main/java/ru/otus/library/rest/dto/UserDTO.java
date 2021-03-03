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
    private String role;

    public UserDTO(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public static User toDomain(UserDTO dto) {
        return new User(dto.getLogin(), dto.getPassword(), dto.getRole());
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(user.getLogin(), user.getPassword(), user.getRole());
    }
}
