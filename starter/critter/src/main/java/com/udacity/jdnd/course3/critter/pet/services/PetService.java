package com.udacity.jdnd.course3.critter.pet.services;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PetService {
    Pet save(Pet pet, long ownerId);
    Pet getById(long petId);
    List<Pet> getAll();
    List<Pet> getAllByIds(List<Long> petIds);
    List<Pet> getAllByOwnerId(long ownerId);
}
