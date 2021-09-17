package com.example.testProject.service;


import com.example.testProject.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DepartmentService {

    Page<Department> findAll(Pageable pageable);

    void saveDepartment(Department department);

    Optional<Department> getDepartmentById(Long id);

    Department getDepartmentByName(String name);

    List<Department> getAllDepartmentByLocation(String location);

    List<Department> getAllDepartment();

    void updateDepartmentData(Department department);

    void deleteDepartment(Long id);

    boolean uniqueName(String name);
}
