package com.example.testProject.repository;

import com.example.testProject.entity.Department;
import com.example.testProject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    Employee findByEmail(String email);

    Employee findByUsername(String username);

    List<Employee> findAll();

    List<Employee> findAllByMarried(boolean married);

    List<Employee> findAllBySalaryAfter(double salary);

    List<Employee> findAllBySalaryBefore(double salary);

    List<Employee> findAllBySalaryBetween(double minSalary, double maxSalary);

    List<Employee> findAllByDepartment(Department department);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee set username = :username where id = :id")
    void updateUsername(@Param("id") Long id, @Param("username") String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee set email = :email where id = :id")
    void updateEmail(@Param("id") Long id, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee set salary = :salary where id = :id")
    void updateSalary(@Param("id") Long id, @Param("salary") double salary);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee set married = :married where id = :id")
    void updateMarried(@Param("id") Long id, @Param("married") boolean married);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee set username = :username, email = :email, salary = :salary, " +
            "married = :married, department =:department where id = :id")
    void updateEmployeeData(@Param("id") Long id,
                            @Param("username") String username,
                            @Param("email") String email,
                            @Param("salary") double salary,
                            @Param("married") boolean married,
                            @Param("department") Department department);
}
