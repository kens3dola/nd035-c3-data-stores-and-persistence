package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = objectMapper.convertValue(petDTO, Pet.class);
        customerRepository.findById(petDTO.getOwnerId()).ifPresent(cust -> {
            if (cust.getPets() == null) cust.setPets(new ArrayList<>());
            pet.setOwner(cust);
            cust.getPets().add(pet);
        });
        return pet.getOwner() == null ? null : this.convert(petRepository.save(pet));
    }

    public PetDTO getPet(long petId) {
        return this.convert(petRepository.findById(petId).get());
    }

    public List<PetDTO> getPets() {
        return petRepository.findAll()
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByOwner(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId)
                .stream().map(this::convert)
                .collect(Collectors.toList());
    }

    private PetDTO convert(Pet pet) {
        return objectMapper.convertValue(pet, PetDTO.class);
    }
}
