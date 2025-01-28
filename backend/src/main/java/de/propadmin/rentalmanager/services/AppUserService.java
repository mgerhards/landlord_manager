package de.propadmin.rentalmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import de.propadmin.rentalmanager.models.AppUser;
import de.propadmin.rentalmanager.repositories.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser save(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
