package com.example.testProject.dto.request;

import com.example.testProject.entity.Department;
import com.example.testProject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequestDto {

    @ApiModelProperty(notes = "Employee id", name = "id")
    private Long id;

    @ApiModelProperty(notes = "Employee username", name = "username")
    private String username;

    @ApiModelProperty(notes = "Employee email", name = "email")
    private String email;

    @ApiModelProperty(notes = "Employee salary", name = "salary")
    private double salary;

    @ApiModelProperty(notes = "Employee married", name = "married")
    private boolean married;

    @ApiModelProperty(notes = "Employee department", name = "department")
    private String department;

    public static Employee fromRequestEmployee(EmployeeRequestDto request, Department department) {
        Employee employee = new Employee();
        employee.setId(request.getId());
        employee.setUsername(request.getUsername());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setMarried(request.isMarried());
        employee.setDepartment(department);
        return employee;
    }
}
