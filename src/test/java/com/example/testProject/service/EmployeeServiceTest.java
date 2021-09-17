package com.example.testProject.service;

import com.example.testProject.entity.AuthEmployee;
import com.example.testProject.entity.Department;
import com.example.testProject.entity.Employee;
import com.example.testProject.exeption.UsernameNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Test
    void saveEmployee() {
        final Employee employeeToSave = new Employee(null, "Rita", "XXX@gmail.com", 500, true, null, null);
        employeeService.saveEmployee(employeeToSave);
        final Employee savedEmployee = employeeService.getEmployeeByEmail("XXX@gmail.com");

        assertNotNull(savedEmployee);
        assertEquals(employeeToSave.getUsername(), savedEmployee.getUsername());
    }

    @Test
    void getEmployeeById() {
        final Employee employeeToSave = new Employee(null, "Vany", "Vany@gmail.com", 600, true, null, null);
        employeeService.saveEmployee(employeeToSave);
        final Employee savedEmployee = employeeService.getEmployeeByEmail("Vany@gmail.com");
        final Employee testEmployee = employeeService.getEmployeeById(savedEmployee.getId()).orElse(null);

        assertNotNull(testEmployee);
        assertEquals(savedEmployee.getUsername(), testEmployee.getUsername());
        assertEquals(savedEmployee.getEmail(), testEmployee.getEmail());
        assertEquals(savedEmployee.getId(), testEmployee.getId());
    }

    @Test
    void getEmployeeByEmail() {
        final Employee employeeToSave = new Employee(null, "Fedy", "Fedy@gmail.com", 600, true, null, null);
        employeeService.saveEmployee(employeeToSave);
        final Employee savedEmployee = employeeService.getEmployeeByEmail("Fedy@gmail.com");

        assertNotNull(savedEmployee);
        assertEquals(savedEmployee.getUsername(), employeeToSave.getUsername());
        assertEquals(savedEmployee.getEmail(), employeeToSave.getEmail());

    }

    @Test
    void getEmployeeByUsername() {
        final Employee employeeToSave = new Employee(null, "Kate", "Kate@gmail.com", 600, true, null, null);
        employeeService.saveEmployee(employeeToSave);
        final Employee savedEmployee = employeeService.getEmployeeByUsername("Kate");

        assertNotNull(savedEmployee);
        assertEquals(savedEmployee.getUsername(), employeeToSave.getUsername());
        assertEquals(savedEmployee.getEmail(), employeeToSave.getEmail());
    }

    @Test
    void getAll() {
        Employee employee6 = new Employee(null, "Gena", "Gena@gmail.com", 700, true,  null, null);
        Employee employee7 = new Employee(null, "Pety", "Pety@gmail.com", 900, true,  null, null);
        Employee employee8 = new Employee(null, "Alex", "Alex@gmail.com", 500, false, null, null);

        employeeService.saveEmployee(employee6);
        employeeService.saveEmployee(employee7);
        employeeService.saveEmployee(employee8);

        List<Employee> employees = employeeService.getAll();
        assertNotNull(employees);
    }

    @Test
    void getAllByMarried() {
        Employee employee3 = new Employee(null, "Zina", "Zina@gmail.com", 400, true, null, null);
        Employee employee4 = new Employee(null, "Yana", "Yana@gmail.com", 800, false, null, null);

        employeeService.saveEmployee(employee3);
        employeeService.saveEmployee(employee4);

        List<Employee> employees = employeeService.getAllByMarried(true);
        assertNotNull(employees);
        for (Employee e : employees) {
            assertTrue(e.isMarried());
        }
    }

    @Test
    void getAllBySalaryAfter() {
        Employee employee3 = new Employee(null, "Vlad", "Vlad@gmail.com", 1000, true, null, null);
        Employee employee4 = new Employee(null, "Juke", "Juke@gmail.com", 300, false, null, null);

        employeeService.saveEmployee(employee3);
        employeeService.saveEmployee(employee4);

        List<Employee> employees = employeeService.getAllBySalaryAfter(700);
        assertNotNull(employees);
        for (Employee e : employees) {
            assertTrue(e.getSalary() >= 700);
        }
    }

    @Test
    void getAllBySalaryBefore() {
        Employee employee3 = new Employee(null, "Valy", "Valy@gmail.com", 1000, true, null, null);
        Employee employee4 = new Employee(null, "Galy", "Galy@gmail.com", 300, false, null, null);

        employeeService.saveEmployee(employee3);
        employeeService.saveEmployee(employee4);

        List<Employee> employees = employeeService.getAllBySalaryBefore(700);
        assertNotNull(employees);
        for (Employee e : employees) {
            assertTrue(e.getSalary() <= 700);
        }
    }

    @Test
    void getAllBySalaryBetween() {
        Employee employee3 = new Employee(null, "Saul", "Saul@gmail.com", 1000, true, null, null);
        Employee employee4 = new Employee(null, "Paul", "Paul@gmail.com", 900, false, null, null);

        employeeService.saveEmployee(employee3);
        employeeService.saveEmployee(employee4);

        List<Employee> employees = employeeService.getAllBySalaryBetween(700, 1000);
        assertNotNull(employees);
        for (Employee e : employees) {
            assertTrue(e.getSalary() >= 700 && e.getSalary() <= 1000);
        }
    }

    @Test
    void getAllByDepartment() {
        Department qa = new Department(null, "CompanyQA", "http://companyQA.com", "Minsk", null, null);
        Department ba = new Department(null, "CompanyBA", "http://companyBA.com", "Minsk", null, null);
        departmentService.saveDepartment(qa);
        departmentService.saveDepartment(ba);

        Employee employee1 = new Employee(null, "EQA1", "EQA1@gmail.com", 500, true,  qa, null);
        Employee employee2 = new Employee(null, "EQA2", "EQA2@gmail.com", 400, false, qa, null);
        Employee employee3 = new Employee(null, "EBA1", "EBA1@gmail.com", 400, false, ba, null);
        employeeService.saveEmployee(employee1);
        employeeService.saveEmployee(employee2);
        employeeService.saveEmployee(employee3);

        employeeService.getAllByDepartment(qa);
        List<Employee> employees = employeeService.getAllByDepartment(qa);
        assertNotNull(employees);
    }

    @Test
    void updateEmployeeData() {
        Employee employeeToSave = new Employee(null, "UPE1", "UPE1@gmail.com", 500, false,  null, null);
        employeeService.saveEmployee(employeeToSave);
        Employee savedEmployee = employeeService.getEmployeeByEmail("UPE1@gmail.com");

        Employee employeeToUpdate= new Employee(savedEmployee.getId(), "UPE2", "UPE2@gmail.com", 700, true,  null, null);
        employeeService.updateEmployeeData(employeeToUpdate);

        Employee updatedEmployee = employeeService.getEmployeeById(savedEmployee.getId()).orElse(null);
        assertNotNull(updatedEmployee);
        assertEquals(employeeToUpdate.getUsername(), updatedEmployee.getUsername());
        assertEquals(employeeToUpdate.getEmail(), updatedEmployee.getEmail());
        assertEquals(employeeToUpdate.getSalary(), updatedEmployee.getSalary());
        assertEquals(employeeToUpdate.isMarried(), updatedEmployee.isMarried());
    }

    @Test
    void checkEmail() throws UsernameNotFoundException {
        Employee employeeToSave = new Employee(null, "CHECK", "CHECK@gmail.com", 500, false,  null, null);
        employeeService.saveEmployee(employeeToSave);
        assertTrue(employeeService.checkEmail("CHECK@gmail.com"));
    }

    @Test
    void deleteEmployee() {
        Employee employeeToSave = new Employee(null, "DEL1", "DEL1@gmail.com", 500, false,  null, null);
        employeeService.saveEmployee(employeeToSave);
        Employee savedEmployee = employeeService.getEmployeeByEmail("DEL1@gmail.com");
        employeeService.deleteEmployee(savedEmployee.getId());
        assertNull(employeeService.getEmployeeByEmail(savedEmployee.getEmail()));
    }
}