package com.udacity.jdnd.course3.critter.user.services;

import com.udacity.jdnd.course3.critter.user.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> getAll();
    Customer getByPetId(Long petId);
}
