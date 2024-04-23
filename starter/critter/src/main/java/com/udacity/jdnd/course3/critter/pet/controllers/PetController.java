package com.udacity.jdnd.course3.critter.pet.controllers;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.entities.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entities.PetMapper;
import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        long ownerId = petDTO.getOwnerId();
        Pet pet = PetMapper.convertPetDTOToPet(petDTO);

        Pet savedPet = petService.save(pet, ownerId);
        return PetMapper.convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getById(petId);
        return PetMapper.convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAll();
        return PetMapper.convertPetToPetDTO(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getAllByOwnerId(ownerId);
        return PetMapper.convertPetToPetDTO(pets);
    }
}
