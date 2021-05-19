package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.User;
import ru.mirea.petstore.Repositories.IProductTypeRepository;
import ru.mirea.petstore.Repositories.IUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository iUserRepository;
    @Captor
    private ArgumentCaptor<User> captor;

    private User user1, user2, user3;
    @BeforeEach
    void init() {
        user1 = new User();
        user1.setId(1);
        user1.setEmail("email");
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setRole("role");

        user2 = new User();
        user2.setId(2);
        user2.setEmail("email");
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setRole("role");

        user3 = new User();
        user3.setId(3);
        user3.setEmail("email");
        user3.setUsername("user3");
        user3.setPassword("password");
        user3.setRole("role");
    }
    @Test
    void loadUserByUsername() {
        Mockito.when(iUserRepository.findByUsername("user2")).thenReturn(user2);
        assertEquals(user2, iUserRepository.findByUsername("user2"));
    }

    @Test
    void create() {
        userService.create("email", "user4", "password");
        Mockito.verify(iUserRepository).save(captor.capture());
        User captured = captor.getValue();
        assertEquals("user4", captured.getUsername());
    }

    @Test
    void getAllUsers() {
        Mockito.when(iUserRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        assertEquals(List.of(user1, user2, user3), iUserRepository.findAll());
    }

    @Test
    void deleteUserByID() {
        userService.deleteUserByID(1);
        Mockito.verify(iUserRepository).deleteById(1);
    }
}