package com.example.testProject.repository;

import com.example.testProject.entity.Company;
import com.example.testProject.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByName(String name);

    List<Company> findAllByLocation(String location);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Company set name = :name where id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Company set website = :website where id = :id")
    void updateWebsite(@Param("id") Long id, @Param("website") String website);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Company set location = :location where id = :id")
    void updateLocation(@Param("id") Long id, @Param("location") String location);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Company set budget = :budget where id = :id")
    void updateBudget(@Param("id") Long id, @Param("budget") double budget);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Company set name = :name, website = :website, location = :location, " +
            "budget = :budget where id = :id")
    void updateCompanyData(@Param("id") Long id,
                            @Param("name") String name,
                            @Param("website") String website,
                            @Param("location") String location,
                            @Param("budget") double budget);
}
