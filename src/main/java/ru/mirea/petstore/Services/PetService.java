package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Repositories.IPetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private IPetRepository iPetRepository;

    @Autowired
    public PetService(IPetRepository iPetRepository) {
        this.iPetRepository = iPetRepository;
    }

    public List<Pet> getAllPets() {
        return iPetRepository.findAll();
    }
    public Pet getPetById(int id) {
        return iPetRepository.findById(id);
    }
    public void addPet(Pet newPet) {
        iPetRepository.save(newPet);
    }
    public void deletePet(int id) {
        iPetRepository.deleteById(id);
    }
}
