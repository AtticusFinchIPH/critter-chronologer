package com.udacity.jdnd.course3.critter.user.controllers;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entities.*;
import com.udacity.jdnd.course3.critter.user.services.CustomerService;
import com.udacity.jdnd.course3.critter.user.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getPetIds() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "petIds must be null");
        }
        Customer customer = CustomerMapper.convertCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.save(customer);
        return  CustomerMapper.convertCustomerToCustomerDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAll();
        return CustomerMapper.convertCustomerToCustomerDTO(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getByPetId(petId);
        return CustomerMapper.convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.convertEmployeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.save(employee);
        return EmployeeMapper.convertEmployeeToEmployeeDTO(savedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getById(employeeId);
        return EmployeeMapper.convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        LocalDate selectedDate = employeeRequestDTO.getDate();
        Set<EmployeeSkill> requiredSkills = employeeRequestDTO.getSkills();
        List<Employee> employees = employeeService.findEmployeesForService(selectedDate, requiredSkills);
        return EmployeeMapper.convertEmployeeToEmployeeDTO(employees);
    }

}
