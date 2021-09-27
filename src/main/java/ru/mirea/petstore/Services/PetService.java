package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Repositories.IPetRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с питомцами в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class PetService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с питомцами
     */
    private IPetRepository iPetRepository;

    /**
     * Конструктор класса-сервиса
     * @param iPetRepository Интерфейс-репозиторий получающий данные из таблицы БД с питомцами
     */
    @Autowired
    public PetService(IPetRepository iPetRepository) {
        this.iPetRepository = iPetRepository;
    }

    /**
     * Метод получает список всех питомцев в БД
     * @return Возвращает списак всех питомцев
     */
    public List<Pet> getAllPets() {
        return iPetRepository.findAll();
    }

    /**
     * Метод получает питомца по идентификатору
     * @param id Идентификато питомца
     * @return Возвразает искомого питомца
     */
    public Pet getPetById(int id) {
        return iPetRepository.findById(id);
    }

    /**
     * Метод добавляет питомца в БД
     * @param newPet Объект нового питомца
     */
    public void addPet(Pet newPet) {
        iPetRepository.save(newPet);
    }

    /**
     * Метод удаляет питомца из БД по идентификатору
     * @param id Идентификатор питомца
     */
    public void deletePet(int id) {
        iPetRepository.deleteById(id);
    }
}
