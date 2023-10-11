package hexlet.code.service.userService;

import hexlet.code.dto.UserDto;
import hexlet.code.model.QUser;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found")); //после сообщения еще есть экспшен
    }

    @Override
    public Iterable<User> showAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        dto.setHashedPwd(getHashedPwd(dto.getPassword()));
        userRepository.save(new User(dto));
        return findByDto(dto);
    }

    @Override
    public User updateUser(long id, UserDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User toUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found")); //после сообщения еще есть экспшен
        toUpdate.setFirstName(dto.getFirstName());
        toUpdate.setLastName(dto.getLastName());
        toUpdate.setEmail(dto.getEmail());
        toUpdate.setPassword(getHashedPwd(dto.getPassword()));
        userRepository.save(toUpdate);
        return findById(id);
    }

    @Override
    public void deleteUser(long id) {
        User toDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found")); //после сообщения еще есть экспшен
        userRepository.deleteById(id);
    }

    private User findByDto(UserDto dto) {
        List<User> usersToReturn = (List) userRepository.findAll(
                QUser.user.firstName.eq(dto.getFirstName())
                        .and(QUser.user.lastName.eq(dto.getLastName()))
                        .and(QUser.user.email.eq(dto.getEmail()))
        );
        if (usersToReturn.size() != 1) {
            throw new ResponseStatusException(HttpStatus.MULTIPLE_CHOICES,
                    "Can't specify User, there are few, with such combination"); //после сообщения еще есть экспшен
        }
        return usersToReturn.get(0);
    }

    private String getHashedPwd(String stringPwd) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(stringPwd.toCharArray(), salt, 2, 32);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        String hashedPwd = Arrays.toString(factory.generateSecret(spec).getEncoded());
        log.warn("§§§§§§ " + stringPwd + " -> " + hashedPwd);
        return hashedPwd;
    }
}
