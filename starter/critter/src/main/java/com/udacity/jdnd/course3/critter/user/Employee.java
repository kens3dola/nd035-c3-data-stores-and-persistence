package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employee extends Person {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    List<Schedule> schedules;
}
