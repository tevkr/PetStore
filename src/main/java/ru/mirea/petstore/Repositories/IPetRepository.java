package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.Pet;

import java.util.List;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с питомцами
 * @author Яновский Владислав
 */
@Repository
public interface IPetRepository extends JpaRepository<Pet, Integer> {
    /**
     * Метод ищущий питомца по идентификатору
     * @param id Идентификатор питомца
     * @return Возвращает искомого питомца
     */
    Pet findById(int id);

    /**
     * Метод удаляющий питомца по идентификатору
     * @param id Идентификатор питомца
     * @return Возвращает рещультат удаления
     */
    Long deleteById(int id);
}
