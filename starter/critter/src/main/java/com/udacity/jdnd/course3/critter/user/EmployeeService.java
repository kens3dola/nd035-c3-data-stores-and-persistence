package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = objectMapper.convertValue(employeeDTO, Employee.class);
        return objectMapper.convertValue(employeeRepository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return objectMapper.convertValue(employeeRepository.findById(employeeId).get(), EmployeeDTO.class);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        });
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        return employeeRepository.findBySkillsInAndDaysAvailable(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills().containsAll(employeeDTO.getSkills()))
                .map(employee -> objectMapper.convertValue(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
