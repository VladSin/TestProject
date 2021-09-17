package com.example.testProject.service;

import com.example.testProject.entity.Department;
import com.example.testProject.entity.Employee;
import com.example.testProject.exeption.UsernameNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {

    Page<Employee> findAll(Pageable pageable);

    void saveEmployee(Employee employee);

    Optional<Employee> getEmployeeById(Long id);

    Employee getEmployeeByEmail(String email);

    Employee getEmployeeByUsername(String name);

    List<Employee> getAll();

    List<Employee> getAllByMarried(boolean married);

    List<Employee> getAllBySalaryAfter(double salary);

    List<Employee> getAllBySalaryBefore(double salary);

    List<Employee> getAllBySalaryBetween(double minSalary, double maxSalary);

    List<Employee> getAllByDepartment(Department department);

    void updateEmployeeData(Employee employee);

    boolean checkEmail(String email) throws UsernameNotFoundException;

    void deleteEmployee(Long id);
}
