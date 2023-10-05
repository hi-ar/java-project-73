package hexlet.code.service.userService;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserService {
    User findById (long id);
    Iterable<User> showAll();
    User createUser (UserDto userDto) throws NoSuchAlgorithmException, InvalidKeySpecException;
    User updateUser(long id, UserDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
    void deleteUser(long id);
}
