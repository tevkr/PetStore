package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Repositories.IPetRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService petService;
    @Mock
    private IPetRepository iPetRepository;
    @Captor
    private ArgumentCaptor<Pet> captor;

    private Pet pet1, pet2;
    @BeforeEach
    void init() {
        pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("pet1");

        pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("pet2");
    }
    @Test
    void getAllPets() {
        Mockito.when(iPetRepository.findAll()).thenReturn(List.of(pet1, pet2));
        assertEquals(2, iPetRepository.findAll().size());
    }

    @Test
    void getPetById() {
        Mockito.when(iPetRepository.findById(1)).thenReturn(pet1);
        assertEquals(pet1, iPetRepository.findById(1));
    }

    @Test
    void addPet() {
        petService.addPet(pet1);
        Mockito.verify(iPetRepository).save(captor.capture());
        Pet captured = captor.getValue();
        assertEquals("pet1", captured.getName());
    }

    @Test
    void deletePet() {
        petService.deletePet(1);
        Mockito.verify(iPetRepository).deleteById(1);
    }
}