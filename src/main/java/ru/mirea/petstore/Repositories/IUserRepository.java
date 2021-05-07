package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    Long deleteById(int id);
}
