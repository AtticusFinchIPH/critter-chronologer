package com.udacity.jdnd.course3.critter.pet.repositories;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByCustomerId(Long customer);
}
