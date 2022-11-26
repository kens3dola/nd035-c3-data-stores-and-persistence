package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final ObjectMapper objectMapper;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.objectMapper = objectMapper;
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = objectMapper.convertValue(customerDTO, Customer.class);
        return objectMapper.convertValue(customerRepository.save(customer), CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(long petId) {
        return petRepository.findById(petId).map(Pet::getOwner).map(owner -> this.convert((Customer) owner)).orElse(null);
    }

    private CustomerDTO convert(Customer customer) {
        CustomerDTO dto = objectMapper.convertValue(customer, CustomerDTO.class);
        dto.setPetIds(customer.getPets() == null ? null : customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return dto;
    }
}
