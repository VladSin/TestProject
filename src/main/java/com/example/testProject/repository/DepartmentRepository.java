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
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name);

    List<Department> findAllByLocation(String location);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Department set name = :name where id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Department set website = :website where id = :id")
    void updateWebsite(@Param("id") Long id, @Param("website") String website);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Department set location = :location where id = :id")
    void updateLocation(@Param("id") Long id, @Param("location") String location);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Department set company = :company where id = :id")
    void updateCompany(@Param("id") Long id, @Param("company") Company company);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Department set name = :name, website = :website, location = :location, company = :company where id = :id")
    void updateDepartmentData(@Param("id") Long id,
                              @Param("name") String name,
                              @Param("website") String website,
                              @Param("location") String location,
                              @Param("company") Company company);
}
