package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udacity.jdnd.course3.critter.pet.Pet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer extends Person{
    @Column(name = "phone_number")
    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Pet> pets;
}
