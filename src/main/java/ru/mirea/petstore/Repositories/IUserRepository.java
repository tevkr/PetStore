package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.User;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
 * @author Яновский Владислав
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    /**
     * Метод ищет пользователя по никнейму
     * @param username Никнейм пользователя
     * @return Возвращает искомого пользователя
     */
    User findByUsername(String username);

    /**
     * Метод удаляет пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
