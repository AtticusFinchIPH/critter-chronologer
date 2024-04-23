package com.udacity.jdnd.course3.critter.user.repositories;

import com.udacity.jdnd.course3.critter.user.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDaysAvailable(DayOfWeek day);
}
