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

/**
 * Класс-сервис для передачи данных из табилцы Бд с пользователями в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    private IUserRepository iUserRepository;
    /**
     * Реализация кодера паролей, который использует функцию сильного хэширования BCrypt
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Конструктор класса-сервиса
     * @param iUserRepository Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    /**
     * Метод получает пользователя из БД по никнейму
     * @param username Никнейм поьзователя
     * @return Возвращает искомого пользователя
     * @throws UsernameNotFoundException Выбрасывается, если реализация UserDetailsService не может найти пользователя по его имени пользователя
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsername(username);
    }

    /**
     * Метод создает новго пользователя
     * @param email Электронная почта пользователя
     * @param username Никнейм поьзователя
     * @param password Пароль пользователя
     */
    public void create(String email, String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setEmail(email);
        u.setRole("USER");
        iUserRepository.save(u);
    }

    /**
     * Метод получает список всех пользователей
     * @return Возвращает список всех пользователей
     */
    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    /**
     * Метод удаляет пользователя из БД
     * @param id Идентификатор пользователя
     */
    public void deleteUserByID(int id) {
        iUserRepository.deleteById(id);
    }

    /**
     * Метод добавляет пользователя в БД
     * @param user Объект пользователя
     */
    public void addUser(User user) {
        iUserRepository.save(user);
    }
}
