package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.User;
import ru.mirea.petstore.Repositories.IUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private IUserRepository iUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }

    public void create(String email, String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setEmail(email);
        u.setRole("USER");
        iUserRepository.save(u);
    }
    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    public void deleteUserByID(int id) {
        iUserRepository.deleteById(id);
    }

    public void addUser(User user) {
        iUserRepository.save(user);
    }
}
