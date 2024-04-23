package com.udacity.jdnd.course3.critter.pet.services;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.entities.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entities.PetMapper;
import com.udacity.jdnd.course3.critter.pet.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import com.udacity.jdnd.course3.critter.user.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public Pet save(Pet pet, long ownerId) {
        Optional<Customer> customer = customerRepository.findById(ownerId);
        if(!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet's Owner does not exist");
        }
        customer.get().addPet(pet);
        pet.setCustomer(customer.get());
        return petRepository.save(pet);
    }

    @Override
    public Pet getById(long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (!pet.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        return pet.get();
    }

    @Override
    public List<Pet> getAllByIds(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }

    @Override
    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getAllByOwnerId(long ownerId) {
        return petRepository.findByCustomerId(ownerId);
    }
}
