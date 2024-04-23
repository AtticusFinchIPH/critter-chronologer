package com.udacity.jdnd.course3.critter.pet.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue
    private long id;
    private PetType type;
    private String name;
    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;
    private LocalDate birthDate;
    private String notes;
}
