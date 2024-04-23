package com.udacity.jdnd.course3.critter.schedule.services;

import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    Schedule save(Schedule schedule);
    List<Schedule> getAll();
    List<Schedule> getAllByPetId(long petId);
    List<Schedule> getAllByEmployeeId(long employeeId);
    List<Schedule> getAllByCustomerId(long customerId);
}
