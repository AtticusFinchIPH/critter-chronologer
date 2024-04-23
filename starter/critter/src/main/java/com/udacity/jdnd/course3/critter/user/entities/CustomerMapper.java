package com.udacity.jdnd.course3.critter.user.entities;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    public static CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> pets = customer.getPets();
        if(pets != null) {
            List<Long> petIds = pets.stream().map(pet -> pet.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }
    public static List<CustomerDTO> convertCustomerToCustomerDTO(List<Customer> customers) {
        return customers.stream().map(CustomerMapper::convertCustomerToCustomerDTO).collect(Collectors.toList());
    }
}
