package com.udacity.jdnd.course3.critter.schedule.services;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repositories.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.entities.Customer;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    };

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getAllByPetId(long petId) {
        return scheduleRepository.getByPetId(petId);
    }

    @Override
    public List<Schedule> getAllByEmployeeId(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found");
        }
        return scheduleRepository.getByEmployeeId(employeeId);
    }

    @Override
    public List<Schedule> getAllByCustomerId(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found");
        }
        List<Pet> pets = customer.get().getPets();
        List<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.getByPetId(pet.getId()));
        }
        return schedules;
    }
}
