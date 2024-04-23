package com.udacity.jdnd.course3.critter.schedule.entities;

import com.udacity.jdnd.course3.critter.pet.entities.Pet;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {
    public static Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO, List<Employee> employees, List<Pet> pets) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }

    public static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeesIds = new ArrayList<>();
        if (employees != null) {
            employeesIds = employees.stream().map(Employee::getId).toList();
        }

        scheduleDTO.setEmployeeIds(employeesIds);

        List<Pet> pets = schedule.getPets();
        List<Long> petsIds = new ArrayList<>();
        if (pets != null) {
            petsIds = pets.stream().map(Pet::getId).toList();
        }

        scheduleDTO.setPetIds(petsIds);

        return scheduleDTO;
    }

    public static List<ScheduleDTO> convertScheduleToScheduleDTO(List<Schedule> schedules) {
        return schedules.stream().map(ScheduleMapper::convertScheduleToScheduleDTO).toList();
    }
}
