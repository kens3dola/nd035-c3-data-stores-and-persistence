package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.udacity.jdnd.course3.critter.user.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private PetType type;
    private String name;
    @Column(name = "owner_id")
    private long ownerId;
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @JsonBackReference
    private Person owner;
}
