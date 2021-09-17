package com.example.testProject.dto.request;

import com.example.testProject.entity.Company;
import com.example.testProject.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentRequestDto {

    @ApiModelProperty(notes = "Department id", name = "id")
    private Long id;

    @ApiModelProperty(notes = "Department name", name = "name")
    private String name;

    @ApiModelProperty(notes = "Department website", name = "website")
    private String website;

    @ApiModelProperty(notes = "Department location", name = "location")
    private String location;

    @ApiModelProperty(notes = "Department company", name = "company")
    private String company;

    public static Department fromRequestDepartment(DepartmentRequestDto request, Company company) {
        Department department = new Department();
        department.setId(request.getId());
        department.setName(request.getName());
        department.setWebsite(request.getWebsite());
        department.setLocation(request.getLocation());
        department.setCompany(company);
        return department;
    }
}
