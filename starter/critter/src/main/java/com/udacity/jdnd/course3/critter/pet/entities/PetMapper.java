package com.udacity.jdnd.course3.critter.pet.entities;

import com.udacity.jdnd.course3.critter.user.entities.Customer;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PetMapper {
    public static Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
    public static PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        Customer customer = pet.getCustomer();
        petDTO.setOwnerId(customer.getId());

        return petDTO;
    }
    public static List<PetDTO> convertPetToPetDTO(List<Pet> pets) {
        return pets.stream().map(PetMapper::convertPetToPetDTO).toList();
    }
}
