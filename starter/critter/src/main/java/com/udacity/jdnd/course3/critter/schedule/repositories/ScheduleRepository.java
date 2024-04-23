package com.udacity.jdnd.course3.critter.schedule.repositories;

import com.udacity.jdnd.course3.critter.schedule.entities.Schedule;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s join s.pets p where p.id = :petId")
    List<Schedule> getByPetId(@Param("petId") Long petId);
    @Query("select s from Schedule s join s.employees e where e.id = :employeeId")
    List<Schedule> getByEmployeeId(@Param("employeeId") Long employeeId);
}
