package com.udacity.jdnd.course3.critter.user.services;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.entities.EmployeeRequestDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {
    Employee save(Employee employeeDTO);
    Employee getById(Long employeeId);
    void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId);
    List<Employee> findEmployeesForService(LocalDate selectedDate, Set<EmployeeSkill> requiredSkills);
}
