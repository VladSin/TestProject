package com.example.testProject.dto.response;

import com.example.testProject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponseDto {

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

    public static EmployeeResponseDto fromEmployee(Employee employee) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setId(employee.getId());
        response.setUsername(employee.getUsername());
        response.setEmail(employee.getEmail());
        response.setSalary(employee.getSalary());
        response.setMarried(employee.isMarried());
        response.setDepartment(employee.getDepartment().getName());
        return response;
    }
}
