package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.Pet;

import java.util.List;

@Repository
public interface IPetRepository extends JpaRepository<Pet, Integer> {
    Pet findById(int id);
    Long deleteById(int id);
}
