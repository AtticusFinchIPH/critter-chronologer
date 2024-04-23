package com.udacity.jdnd.course3.critter.schedule.controllers;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import com.udacity.jdnd.course3.critter.schedule.entities.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entities.ScheduleMapper;
import com.udacity.jdnd.course3.critter.schedule.services.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.services.EmployeeService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if (scheduleDTO.getEmployeeIds() == null || scheduleDTO.getPetIds() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EmployeeIds & PetId must not be empty");
        }

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = petService.getAllByIds(petIds);
        if(petIds.size() != pets.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect petIds");
        }

        LocalDate selectedDate = scheduleDTO.getDate();
        Set<EmployeeSkill> requiredSkills = scheduleDTO.getActivities();
        List<Employee> availableEmployees = employeeService.findEmployeesForService(selectedDate, requiredSkills);

        List<Long> wantedEmployeeIds = scheduleDTO.getEmployeeIds();
        Optional<Long> unmatchedEmployeeId = wantedEmployeeIds.stream().filter(wantedId -> {
           Optional<Employee> matchedEmployee = availableEmployees.stream().filter(available -> available.getId() == wantedId).findAny();
           if (!matchedEmployee.isPresent()) {
               return true;
           }
           return false;
        }).findAny();
        if(unmatchedEmployeeId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unavailable or Unqualified skilled Employees, please find others");
        };

        Schedule stagedSchedule = ScheduleMapper.convertScheduleDTOToSchedule(scheduleDTO, availableEmployees, pets);
        Schedule createdSchedule = scheduleService.save(stagedSchedule);
        return ScheduleMapper.convertScheduleToScheduleDTO(createdSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAll();
        return ScheduleMapper.convertScheduleToScheduleDTO(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getAllByPetId(petId);
        return ScheduleMapper.convertScheduleToScheduleDTO(scheduleList);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getAllByEmployeeId(employeeId);
        return ScheduleMapper.convertScheduleToScheduleDTO(scheduleList);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getAllByCustomerId(customerId);
        return ScheduleMapper.convertScheduleToScheduleDTO(scheduleList);
    }
}
