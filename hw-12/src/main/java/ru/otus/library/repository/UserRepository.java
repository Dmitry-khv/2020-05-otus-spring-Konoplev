package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.rest.dto.UserDTO;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDTO, String> {
    Optional<UserDTO> findByLoginAndPassword(String login, String password);
}
