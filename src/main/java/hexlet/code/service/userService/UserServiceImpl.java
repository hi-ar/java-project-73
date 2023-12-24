package hexlet.code.service.userService;

import hexlet.code.dto.UserDto;
import hexlet.code.exception.ExistsException;
import hexlet.code.exception.ForbiddenException;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserUtils userUtils;

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found")); //после сообщения еще есть экспшен
        if (!userUtils.currentHasAccessTo(user)) {
            throw new ForbiddenException("User with id " + id + " is not accessible with current login: "
                    + userUtils.getCurrentLogin());
        }
        return user;
    }

    @Override
    public Iterable<User> showAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDto dto) { //validated
        User newUser = new User(dto);
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ExistsException("User with email " + newUser.getEmail() + " already exists");
        }
        newUser.setPasswordDigest(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User updateUser(long id, UserDto dto) { //validated
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found"));

        if (!userUtils.currentHasAccessTo(userToUpdate)) {
            throw new ForbiddenException("User with id " + id + " is not accessible with current login: "
                    + userUtils.getCurrentLogin());
        }
        userToUpdate.setFirstName(dto.getFirstName());
        userToUpdate.setLastName(dto.getLastName());
        userToUpdate.setEmail(dto.getEmail());
        userToUpdate.setPasswordDigest(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(userToUpdate);
        return findById(id);
    }

    @Override
    public void deleteUser(long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "User with id " + id + " not found"));
        if (!userUtils.currentHasAccessTo(userToDelete)) {
            throw new ForbiddenException("User with id " + id + " is not accessible with current login: "
                    + userUtils.getCurrentLogin());
        }
        userRepository.deleteById(id);
    }

//    private String getHashedPwd(String stringPwd) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(stringPwd.toCharArray(), salt, 2, 32);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        String hashedPwd = Arrays.toString(factory.generateSecret(spec).getEncoded());
//        log.info("§§§§§§ " + stringPwd + " -> " + hashedPwd);
//        return hashedPwd;
//    }
}
