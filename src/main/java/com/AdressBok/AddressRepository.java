package com.AdressBok;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    Address findByName(String name);

    @Query("SELECT a FROM Address a WHERE a.birthYear BETWEEN :startYear AND :endYear")
    List<Address> findBetweenYears(@Param("startYear") int startYear, @Param("endYear") int endYear);
}
