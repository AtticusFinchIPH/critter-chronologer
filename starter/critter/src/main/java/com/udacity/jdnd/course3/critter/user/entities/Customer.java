package com.udacity.jdnd.course3.critter.user.entities;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "customer", targetEntity = Pet.class)
    private List<Pet> pets;
    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }
}
