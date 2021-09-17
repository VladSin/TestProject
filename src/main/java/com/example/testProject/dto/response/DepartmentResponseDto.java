package com.example.testProject.dto.response;

import com.example.testProject.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentResponseDto {

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

    public static DepartmentResponseDto fromDepartment(Department department) {
        DepartmentResponseDto response = new DepartmentResponseDto();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setWebsite(department.getWebsite());
        response.setLocation(department.getLocation());
        response.setCompany(department.getCompany().getName());
        return response;
    }
}
