package com.udacity.jdnd.course3.critter.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final ObjectMapper objectMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository, ObjectMapper objectMapper) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.objectMapper = objectMapper;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = objectMapper.convertValue(scheduleDTO, Schedule.class);
        schedule.setEmployees(employeeRepository.findAllById(scheduleDTO.getEmployeeIds()));
        schedule.setPets(petRepository.findAllById(scheduleDTO.getPetIds()));
        schedule.setSkills(scheduleDTO.getActivities());
        return this.convert(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        return scheduleRepository.findALlByPetId(petId).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployeeId(employeeId).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        return scheduleRepository
                .findAllByPetIdIn(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()))
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convert(Schedule schedule) {
        ScheduleDTO dto = objectMapper.convertValue(schedule, ScheduleDTO.class);
        dto.setEmployeeIds(schedule.getEmployees().stream().map(Person::getId).collect(Collectors.toList()));
        dto.setActivities(schedule.getSkills());
        dto.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return dto;
    }
}
