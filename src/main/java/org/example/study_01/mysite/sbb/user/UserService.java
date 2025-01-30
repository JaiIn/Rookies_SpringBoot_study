package org.example.study_01.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String email) {
        SiteUser User = new SiteUser();
        User.setUsername(username);
        User.setEmail(email);
        User.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(User);
        return User;
    }
}
