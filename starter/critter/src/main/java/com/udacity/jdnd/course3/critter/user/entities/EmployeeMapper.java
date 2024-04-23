package com.udacity.jdnd.course3.critter.user.entities;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static Employee convertEmployeeDTOToEmployee (EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    public static List<EmployeeDTO> convertEmployeeToEmployeeDTO(List<Employee> employees) {
        return employees.stream().map(EmployeeMapper::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

}
