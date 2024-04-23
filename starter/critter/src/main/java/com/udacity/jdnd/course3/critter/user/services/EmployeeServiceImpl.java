package com.udacity.jdnd.course3.critter.user.services;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entities.Employee;
import com.udacity.jdnd.course3.critter.user.entities.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return  employee.get();
    }

    @Override
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getById(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }

    @Override
    public List<Employee> findEmployeesForService(LocalDate selectedDate, Set<EmployeeSkill> requiredSkills) {
        List<Employee> availableEmployees = new ArrayList<>();

        List<Employee> employees = employeeRepository.findByDaysAvailable(selectedDate.getDayOfWeek());
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(requiredSkills)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
