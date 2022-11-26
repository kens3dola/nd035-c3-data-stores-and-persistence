package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "select s from Schedule s inner join s.pets p where p.id = :petId")
    List<Schedule> findALlByPetId(long petId);


    @Query(value = "select s from Schedule s inner join s.employees e where e.id = :employeeId")
    List<Schedule> findAllByEmployeeId(long employeeId);

    @Query(value = "select s from Schedule s inner join s.pets p where p.id in :petIds")
    List<Schedule> findAllByPetIdIn(List<Long> petIds);
}
