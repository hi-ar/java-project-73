package hexlet.code.service.userDetailsService;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsManager { //UserDetailsService было
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, //422
                        "User with email " + email + " not found"));

        List<SimpleGrantedAuthority> default_authorities = List.of(new SimpleGrantedAuthority("default"));

        UserDetails ud = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPasswordDigest(), default_authorities);

        return ud;
    }

    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method createUser() in UDServiceImml");
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method updateUser() in UDServiceImml");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method deleteUser() in UDServiceImml");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unimplemented method changePwd() in UDServiceImml");
    }

    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException("Unimplemented method UserExists() in UDServiceImml");
    }
}
